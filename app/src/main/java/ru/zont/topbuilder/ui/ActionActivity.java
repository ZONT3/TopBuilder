package ru.zont.topbuilder.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.concurrent.atomic.AtomicBoolean;

import ru.zont.topbuilder.R;
import ru.zont.topbuilder.core.TopBuilder;
import ru.zont.topbuilder.core.TrackableProgress;
import ru.zont.topbuilder.core.implement.WeightTopBuilder;
import ru.zont.topbuilder.ui.data.TopBuilderInfo;
import ru.zont.topbuilder.ui.data.TopItem;
import ru.zont.topbuilder.ui.data.TopList;

public class ActionActivity extends AppCompatActivity {

    public static final String FRAGMENT_TAG = "action";

    private final AtomicBoolean decisionUnlocked = new AtomicBoolean(false);
    private boolean firstPair = true;

    private TopBuilder<TopItem> topInstance;
    private ActionFragment activeFragment;

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

    private void nextPair() {
        final Thread thread = new Thread(() -> { // TODO re-use thread
            if (topInstance.hasNext()) {
                topInstance.next((lhs, rhs) -> runOnUiThread(() -> transitToNext(lhs, rhs)));
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

    private void transitToNext(TopItem lhs, TopItem rhs) {
        if (firstPair) {
            firstPair = false;
        } else {
            final ActionFragment newFragment = ActionFragment.newInstance();
            newFragment.setContent(lhs, rhs);
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.action_container, newFragment, FRAGMENT_TAG)
                    .commit();
            activeFragment = newFragment;
        }
        activeFragment.setContent(lhs, rhs);
        activeFragment.setOnClick(this::onClickDecision);
    }

    public void onClickDecision(View v) {
        if (!decisionUnlocked.get()) return;
        final boolean negate = "lhs".equals(v.getTag(R.id.tag_hand_side));
        topInstance.provideDecision(negate ? -1 : 1);
        decisionUnlocked.set(false);
        nextPair();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (topInstance.hasNext()) {
            topInstance.undo();
            nextPair();
        } else {
            super.onBackPressed();
        }
    }
}