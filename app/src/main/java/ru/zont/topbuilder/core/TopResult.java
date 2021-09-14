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
    public void forEach(@NonNull BiConsumer<? super Integer, ? super T> action) {
        for (int i = 1; map.containsKey(i); i++){
            final ArrayList<T> ts = map.get(i);
            for (T t : ts != null ? ts : (List<T>) Collections.emptyList()) {
                action.accept(i, t);
            }
        }
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

    public interface BiConsumer<T, U> {
        void accept(T t, U u);
    }
}
