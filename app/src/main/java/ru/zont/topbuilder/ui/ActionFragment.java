package ru.zont.topbuilder.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.zont.topbuilder.R;
import ru.zont.topbuilder.ui.data.TopItem;

public class ActionFragment extends Fragment {
    private ImageView lhsView;
    private ImageView rhsView;
    private TextView lhsTitle;
    private TextView rhsTitle;

    private TopItem lhs;
    private TopItem rhs;

    private View.OnClickListener onClick;


    public static ActionFragment newInstance() {
        return new ActionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.action_fragment, container, false);

        lhsView = v.findViewById(R.id.act_lhs);
        rhsView = v.findViewById(R.id.act_rhs);
        lhsView.setTag(R.id.tag_hand_side, "lhs");
        rhsView.setTag(R.id.tag_hand_side, "rhs");

        lhsTitle = v.findViewById(R.id.act_lhs_title);
        rhsTitle = v.findViewById(R.id.act_rhs_title);

        applyOnClick();
        applyContent();

        return v;
    }

    public void setContent(TopItem lhs, TopItem rhs) {
        this.lhs = lhs;
        this.rhs = rhs;

        final FragmentActivity activity = getActivity();
        if (activity != null)
            activity.runOnUiThread(this::applyContent);
    }

    private void applyContent() {
        if (this.lhsView != null && this.rhsView != null && lhs != null && rhs != null) {
            Picasso.with(lhsView.getContext())
                    .load(lhs.getImage())
                    .placeholder(android.R.drawable.stat_notify_sync)
                    .error(android.R.drawable.ic_dialog_alert)
                    .into(lhsView);
            Picasso.with(rhsView.getContext())
                    .load(rhs.getImage())
                    .placeholder(android.R.drawable.stat_notify_sync)
                    .error(android.R.drawable.ic_dialog_alert)
                    .into(rhsView);

            lhsTitle.setText(lhs.getTitle());
            rhsTitle.setText(rhs.getTitle());
        }
    }

    public void setOnClick(View.OnClickListener onClick) {
        this.onClick = onClick;
        applyOnClick();
    }

    private void applyOnClick() {
        if (lhsView != null && rhsView != null && onClick != null) {
            lhsView.setOnClickListener(onClick);
            rhsView.setOnClickListener(onClick);
        }
    }
}