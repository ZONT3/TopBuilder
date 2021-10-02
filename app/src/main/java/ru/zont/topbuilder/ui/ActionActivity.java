package ru.zont.topbuilder.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicBoolean;

import ru.zont.topbuilder.R;
import ru.zont.topbuilder.core.DecisionEntry;
import ru.zont.topbuilder.core.TopBuilder;
import ru.zont.topbuilder.core.TrackableProgress;
import ru.zont.topbuilder.core.implement.BasicTopBuilder;
import ru.zont.topbuilder.core.implement.WeightTopBuilder;
import ru.zont.topbuilder.ui.data.TopBuilderInfo;
import ru.zont.topbuilder.ui.data.TopItem;
import ru.zont.topbuilder.ui.data.TopList;

public class ActionActivity extends AppCompatActivity {

    public static final String FRAGMENT_TAG = "action";
    public static final String HISTORY_TAG = "history";

    private final AtomicBoolean decisionUnlocked = new AtomicBoolean(false);
    private boolean firstPair = true;

    private TopBuilder<TopItem> topInstance;
    private ActionFragment activeFragment;
    private int wantToChangePos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_activity);
        if (savedInstanceState == null) {
            activeFragment = ActionFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.action_container, activeFragment, "action")
                    .commitNow();
        } else {
            activeFragment = (ActionFragment) getSupportFragmentManager().findFragmentByTag("action");
        }

        final Intent intent = getIntent();
        final TopList list = intent.getParcelableExtra("z.top.list");

        TopBuilderInfo<? extends TopBuilder<? extends TopItem>> info = MainActivity.getTopBuilderInfo(intent);

        if (list == null || info == null) {
            Log.e("ActionActivity", "Critical extras not provided");
            finish();
            return;
        }

        setupAction(list, info);
    }

    @SuppressWarnings("unchecked")
    private void setupAction(TopList list, TopBuilderInfo<? extends TopBuilder<? extends TopItem>> info) {
        topInstance = (TopBuilder<TopItem>) info.newBuilderInstance(this, list);

        if (topInstance instanceof TrackableProgress) {
            ((TrackableProgress) topInstance).setProgressListener(this::onProgressChange);
        }

        nextPair();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    private void nextPair() {
        final Thread thread = new Thread(() -> { // TODO re-use thread
            if (topInstance instanceof BasicTopBuilder) {
                final BasicTopBuilder<TopItem> t =
                        (BasicTopBuilder<TopItem>) this.topInstance;
                while (t.tryAutoDecision());
            }
            if (topInstance.hasNext()) {
                topInstance.next((lhs, rhs) ->
                        runOnUiThread(() ->
                                transitToNext(lhs, rhs, false)));
                decisionUnlocked.set(true);
            } else {
                runOnUiThread(this::topDone);
            }
        }, "Next pair handler");
        thread.setDaemon(true);
        thread.start();
    }

    private void topDone() {
        final ResultsFragment fragment = ResultsFragment.newInstance(topInstance.getResults());
        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.action_container, fragment, FRAGMENT_TAG)
                .commit();
        final ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setSubtitle(null);
    }

    private void onProgressChange(int i, int total) {
        runOnUiThread(() -> {
            final ActionBar ab = getSupportActionBar();
            if (ab == null || topInstance == null) return;

            String str = topInstance instanceof WeightTopBuilder
                    ? getString(R.string.action_progress_weight)
                    : getString(R.string.action_progress);
            ab.setSubtitle(String.format(str, i, total));
        });
    }

    private void transitToNext(TopItem lhs, TopItem rhs, boolean addToBackStack) {
        if (firstPair) {
            firstPair = false;
        } else {
            final ActionFragment newFragment = ActionFragment.newInstance();
            newFragment.setContent(lhs, rhs);

            final FragmentManager fm = getSupportFragmentManager();
            final FragmentTransaction transaction = fm
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.action_container, newFragment, FRAGMENT_TAG);
            if (addToBackStack)
                transaction.addToBackStack(null);
            else {
                final Fragment f = fm.findFragmentByTag(HISTORY_TAG);
                if (f != null) transaction.remove(f);
            }
            transaction.commit();
            activeFragment = newFragment;
        }
        activeFragment.setContent(lhs, rhs);
        activeFragment.setOnClick(this::onClickDecision);
    }

    public void onClickDecision(View v) {
        if (!decisionUnlocked.get()) return;
        final boolean negate = "lhs".equals(v.getTag(R.id.tag_hand_side));
        final int decision = negate ? -1 : 1;

        if (wantToChangePos >= 0 && topInstance instanceof BasicTopBuilder) {
            final boolean b = ((BasicTopBuilder<TopItem>) topInstance).changeDecision(wantToChangePos, decision);
            wantToChangePos = -1;
            if (b) {
                onBackPressed();
                return;
            }
        } else topInstance.provideDecision(decision);

        decisionUnlocked.set(false);
        nextPair();
    }

    private void onHistoryItemClick(DecisionEntry<TopItem> entry, int pos) {
        if (!(topInstance instanceof BasicTopBuilder)) {
            Toast.makeText(this, R.string.history_change_not_supported, Toast.LENGTH_LONG).show();
            return;
        }

        wantToChangePos = pos;
        transitToNext(entry.getLhs(), entry.getRhs(), true);

        decisionUnlocked.set(true);
    }

    @Override
    public void onBackPressed() {
        final FragmentManager fm = getSupportFragmentManager();
        final Fragment f = fm.findFragmentByTag(HISTORY_TAG);

        final int count = fm.getBackStackEntryCount();
        final boolean atHistory = count > 0 &&
                HISTORY_TAG.equals(fm.getBackStackEntryAt(count - 1).getName());

        if (atHistory || f == null && topInstance.hasNext()) {
            if (!atHistory) topInstance.undo();
            else fm.popBackStack();
            nextPair();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.act_menuitem_history) {
            if (getSupportFragmentManager().findFragmentByTag(HISTORY_TAG) != null)
                return true;

            final HistoryFragment fragment = new HistoryFragment(topInstance);
            fragment.setOnItemClickListener(this::onHistoryItemClick);
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.action_container, fragment, HISTORY_TAG)
                    .addToBackStack(HISTORY_TAG)
                    .commit();
        } else return super.onOptionsItemSelected(item);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action, menu);
        return super.onCreateOptionsMenu(menu);
    }
}