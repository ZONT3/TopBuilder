package ru.zont.topbuilder.core;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TopResult<T> {
    private final HashMap<Integer, ArrayList<T>> map = new HashMap<>();

    @SuppressWarnings("UnusedReturnValue")
    ArrayList<T> put(Integer key, T value) {
        final ArrayList<T> ts = map.get(key);
        final ArrayList<T> list = ts != null ? ts : new ArrayList<>(2);
        list.add(value);
        return map.put(key, list);
    }

    @SuppressWarnings("unchecked")
    public void forEach(@NonNull TopBuilder.BiConsumer<Integer, T> action) {
        forEachList((i, ts) -> {
            for (T t : ts != null ? ts : (List<T>) Collections.emptyList()) {
                action.accept(i, t);
            }
        });
    }

    public void forEachList(@NonNull TopBuilder.BiConsumer<Integer, ArrayList<T>> action) {
        for (int i = 1; map.containsKey(i); i++){
            final ArrayList<T> ts = map.get(i);
            if (ts != null)
                action.accept(i, ts);
        }
    }

    public ArrayList<T> get(int i) {
        return map.get(i+1);
    }

    public int size() {
        return map.size();
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        forEach((i, t) -> sb
                .append(i)
                .append(": ")
                .append(t.toString())
                .append(", "));
        return sb.toString();
    }

}
