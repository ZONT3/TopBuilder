package ru.zont.topbuilder.ui.data;

import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.zont.topbuilder.databinding.FragmentTopItemBinding;

public class TopItemHolder extends ArrayList<TopItem> {
    private final ArrayList<FragmentTopItemBinding> list;
    private final int i;

    public TopItemHolder(ArrayList<TopItem> l, int index, LayoutInflater inflater, ViewGroup parent) {
        super(l);

        list = new ArrayList<>(l.size());
        this.i = index;

        for (int i = 0; i < l.size(); i++) {
            final TopItem item = get(i);
            FragmentTopItemBinding b = FragmentTopItemBinding.inflate(inflater, parent, false);
            b.divider2.setVisibility(i > 0 ? View.VISIBLE : View.GONE);
            b.topItemTitle.setText(item.getTitle());
            Picasso.with(b.topItemThumb.getContext())
                    .load(item.getImage())
                    .into(b.topItemThumb);
            list.add(b);
        }
    }

    public ArrayList<FragmentTopItemBinding> getBindings() {
        return list;
    }

    public int getIndex() {
        return i;
    }
}
