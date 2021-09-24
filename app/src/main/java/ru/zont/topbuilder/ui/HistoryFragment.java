package ru.zont.topbuilder.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.zont.topbuilder.R;
import ru.zont.topbuilder.core.TopBuilder;
import ru.zont.topbuilder.core.TopResult;
import ru.zont.topbuilder.ui.data.TopItem;

public class HistoryFragment extends Fragment {

    private TopBuilder<TopItem> topBuilder;

    public HistoryFragment() { }

    public HistoryFragment(TopBuilder<TopItem> topBuilder) {
        this.topBuilder = topBuilder;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new DecisionsAdapter(topBuilder));
        }
        return view;
    }
}