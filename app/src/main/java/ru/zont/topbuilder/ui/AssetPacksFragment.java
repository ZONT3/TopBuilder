package ru.zont.topbuilder.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.zont.topbuilder.R;
import ru.zont.topbuilder.core.TopBuilder;
import ru.zont.topbuilder.ui.data.TopItem;

public class AssetPacksFragment extends Fragment {
    public AssetPacksFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asset_packs, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

//            adapter = new DecisionsAdapter(topBuilder);
//            adapter.setOnClickListener(onItemClickListener);
//            recyclerView.setAdapter(adapter);
        }
        return view;
    }

//    public void setOnItemClickListener(DecisionsAdapter.OnClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//        if (adapter != null)
//            adapter.setOnClickListener(this.onItemClickListener);
//    }
}
