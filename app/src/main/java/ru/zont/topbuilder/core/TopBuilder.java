package ru.zont.topbuilder.core;

import java.util.List;

public interface TopBuilder<T> {
    void next(Comparator<T> compare);

    void undo();

    default void undo(int count) {
        for (int i = 0; i < count; i++)
            undo();
    }

    boolean hasNext();

    List<T> getResults();

    interface Comparator<T> {
        int apply(T lhs, T rhs);
    }
}
