package ru.zont.topbuilder.core.implement;

import ru.zont.topbuilder.core.DecisionEntry;
import ru.zont.topbuilder.core.TopResult;
import ru.zont.topbuilder.core.TrackableProgress;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class KothTopBuilder<T> extends BasicTopBuilder<T> implements TrackableProgress {

    private final Deque<T> entryList;
    private final int size;
    private List<T> kings;

    public KothTopBuilder(List<T> list) {
        size = list.size();
        this.entryList = new ArrayDeque<>(list);
    }

    @Override
    public void next(Supplier<T> supplier) {
        if (kings == null) kings = new ArrayList<>();
        if (kings.size() == 0) kings.add(entryList.pop());
        supplier.provide(kings.get(0), entryList.peek());

        acceptProgress(size - entryList.size(), size - 1);
    }

    @Override
    public void provideDecision(int decision) {
        final T pop = entryList.pop();
        final List<T> currKings = new ArrayList<>(kings);
        if (decision > 0) kings.clear();
        if (decision >= 0) kings.add(pop);

        addUndoAction(() -> {
            entryList.addFirst(pop);
            kings = currKings;
        });

        addHistoryEntry(new DecisionEntry<>(kings.get(0), pop, decision));
    }

    @Override
    public boolean hasNext() {
        return !entryList.isEmpty();
    }

    @Override
    public TopResult<T> getResults() {
        TopResult<T> res = new TopResult<T>();
        for (T k : kings)
            res.put(1, k);
        return res;
    }
}