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
import ru.zont.topbuilder.core.TopResult;
import ru.zont.topbuilder.ui.data.TopItem;

public class ResultsFragment extends Fragment {
    private final TopResult<TopItem> result;

    public ResultsFragment(TopResult<TopItem> result) {
        this.result = result;
    }

    @SuppressWarnings("unused")
    public static ResultsFragment newInstance(TopResult<TopItem> result) {
        return new ResultsFragment(result);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new ResultsAdapter(result, getLayoutInflater(), recyclerView));
        }
        return view;
    }
}