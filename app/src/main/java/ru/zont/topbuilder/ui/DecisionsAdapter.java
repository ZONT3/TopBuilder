package ru.zont.topbuilder.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import ru.zont.topbuilder.core.DecisionEntry;
import ru.zont.topbuilder.core.TopBuilder;
import ru.zont.topbuilder.core.implement.RatingDecisionEntry;
import ru.zont.topbuilder.databinding.FragmentDecisionBinding;
import ru.zont.topbuilder.ui.data.TopItem;

public class DecisionsAdapter extends RecyclerView.Adapter<DecisionsAdapter.ViewHolder> {
    private final TopBuilder<TopItem> topBuilder;
    private OnClickListener onClickListener;

    public DecisionsAdapter(TopBuilder<TopItem> topBuilder) {
        this.topBuilder = topBuilder;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentDecisionBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DecisionEntry<TopItem> entry = topBuilder.getHistory().get(position);
        final FragmentDecisionBinding b = holder.binding;
        final Context context = b.getRoot().getContext();

        final int decision = entry.getDecision();
        b.decChevLhs.setVisibility(decision <= 0 ? View.VISIBLE : View.GONE);
        b.decChevRhs.setVisibility(decision >= 0 ? View.VISIBLE : View.GONE);
        b.decWeight.setVisibility(decision >= -1 & decision <= 1 ? View.GONE : View.VISIBLE);

        Picasso.with(context)
                .load(entry.getLhs().getImage())
                .placeholder(android.R.drawable.stat_notify_sync)
                .error(android.R.drawable.ic_dialog_alert)
                .into(b.decLhs);

        Picasso.with(context)
                .load(entry.getRhs().getImage())
                .placeholder(android.R.drawable.stat_notify_sync)
                .error(android.R.drawable.ic_dialog_alert)
                .into(b.decRhs);


        final String lhsTitle = entry.getLhs().getTitle();
        final String rhsTitle = entry.getRhs().getTitle();
        final int maxLen = Math.max(lhsTitle.length(), rhsTitle.length());

        SpannableStringBuilder sb = new SpannableStringBuilder();
        if (entry instanceof RatingDecisionEntry) {
            final RatingDecisionEntry<TopItem> e = (RatingDecisionEntry<TopItem>) entry;

            final ForegroundColorSpan redSpan = new ForegroundColorSpan(0xFFF00000);
            final ForegroundColorSpan greenSpan = new ForegroundColorSpan(0xFF00F000);

            final float deltaLhs = e.getRatingDeltaLhs();
            final float deltaRhs = e.getRatingDeltaRhs();
            final String deltaLhsStr = setw(
                    (deltaLhs >= 0 ? "+" : "-") +
                            String.format("%.01f", Math.abs(deltaLhs)),
                    5);
            final String deltaRhsStr = setw(
                    (deltaRhs >= 0 ? "+" : "-")
                            + String.format("%.01f", Math.abs(deltaRhs)),
                    5);

            final boolean noSnip = maxLen > 24;


            sb.append(noSnip ? lhsTitle : setw(lhsTitle, maxLen))
                    .append(" ");

            if (deltaLhs != 0)
                sb.append(deltaLhsStr,
                        deltaRhs < 0 ? redSpan : greenSpan,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            else
                sb.append(deltaLhsStr);

            sb.append(" = ")
                    .append(setw(String.valueOf(e.getRatingCurrentLhs()), 4))
                    .append("\n");


            sb.append(noSnip ? rhsTitle : setw(rhsTitle, maxLen))
                    .append(" ");

            if (deltaRhs != 0)
                sb.append(deltaRhsStr,
                        deltaRhs < 0 ? redSpan : greenSpan,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            else
                sb.append(deltaRhsStr);

            sb.append(" = ")
                    .append(setw(String.valueOf(e.getRatingCurrentRhs()), 4))
                    .append("\n");
        } else {
            final Object span = new BackgroundColorSpan(0x6000F000);
            final String vsStr = " vs. ";
            if (decision < 0)
                sb.append(lhsTitle, span, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            else sb.append(lhsTitle);
            sb.append(vsStr);
            if (decision > 0)
                sb.append(rhsTitle, span, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            else sb.append(rhsTitle);
            sb.append("\n");
        }
        b.decTooltip.setText(sb);

        b.getRoot().setOnClickListener((v) -> {
            if (onClickListener != null)
                onClickListener.onClick(entry, position);
        });
    }

    @Override
    public int getItemCount() {
        return topBuilder.getHistory().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final FragmentDecisionBinding binding;

        public ViewHolder(FragmentDecisionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @SuppressLint("DefaultLocale")
    private static String setw(String s, int w) {
        return String.format(String.format("%%%ds", w), s);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    interface OnClickListener {
        void onClick(DecisionEntry<TopItem> entry, int pos);
    }
}
