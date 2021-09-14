package ru.zont.topbuilder.ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.zont.topbuilder.core.TopResult;
import ru.zont.topbuilder.databinding.FragmentResultsPlaceBinding;
import ru.zont.topbuilder.databinding.FragmentTopItemBinding;
import ru.zont.topbuilder.ui.data.TopItem;
import ru.zont.topbuilder.ui.data.TopItemHolder;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {

    private final ArrayList<TopItemHolder> mValues;

    public ResultsAdapter(TopResult<TopItem> items, LayoutInflater inflater, ViewGroup parent) {
        mValues = new ArrayList<>(items.size());
        items.forEachList((i, l) -> mValues.add(new TopItemHolder(l, i, inflater, parent)));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentResultsPlaceBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final TopItemHolder item = mValues.get(position);
        holder.placeView.setText(item.getIndex()+"");
        holder.listView.removeAllViews();
        for (FragmentTopItemBinding b: item.getBindings()) {
            holder.listView.addView(b.getRoot());
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView placeView;
        public final LinearLayout listView;

        public ViewHolder(FragmentResultsPlaceBinding binding) {
            super(binding.getRoot());
            placeView = binding.resultPlaceN;
            listView = binding.resultPlaceList;
        }
    }
}