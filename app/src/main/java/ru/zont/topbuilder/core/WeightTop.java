package ru.zont.topbuilder.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;

public class WeightTop<T> extends BasicTopBuilder<T> {
    private final ArrayList<Entry> list;

    private Entry currLhs;
    private Entry currRhs;
    private boolean validation = false;

    public WeightTop(List<T> list) {
        this.list = wrap(list);
    }

    @Override
    public void next(Comparator<T> compare) {
        if (!hasNext()) return;

        final Entry mCurrLhs = this.currLhs;
        final Entry mCurrRhs = this.currRhs;

        final int weight = compare.apply(mCurrLhs.value, mCurrRhs.value);
        final Entry applyTo = weight > 0 ? mCurrRhs : mCurrLhs;

        if (weight == 0) {
            mCurrRhs.addToSkip(mCurrLhs);
            mCurrLhs.addToSkip(mCurrRhs);

            addUndoAction(() -> {
                mCurrRhs.cancelSkip(mCurrLhs);
                mCurrLhs.cancelSkip(mCurrRhs);
            });
        } else {
            final int abs = Math.abs(weight);
            applyTo.weight += abs;
            addUndoAction(() -> applyTo.weight -= abs);
        }

        validation = false;
    }

    @Override
    public boolean hasNext() {
        validate();
        return (currLhs != null && currRhs != null);
    }

    @Override
    public List<T> getResults() {
        return unwrap(list);
    }

    @Override
    public void undo() {
        validation = false;
        super.undo();
    }

    private void findNext() {
        currLhs = null;
        currRhs = null;

        final int n = list.size();
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                Entry lhs = list.get(j);
                Entry rhs = list.get(j + 1);
                if (lhs.weight < rhs.weight) {
                    list.set(j, rhs);
                    list.set(j+1, lhs);
                } else if (lhs.weight == rhs.weight && !lhs.shouldSkip(rhs)) {
                    currLhs = lhs;
                    currRhs = rhs;
                    return;
                }
            }
        }
    }

    private void validate() {
        if (!validation) findNext();
        validation = true;
    }

    private List<T> unwrap(ArrayList<Entry> list) {
        if (hasNext()) {
            list = new ArrayList<>(list);
            Collections.sort(list, (l, r) -> r.weight - l.weight);
        }
        ArrayList<T> ts = new ArrayList<>();
        for (Entry e : list) ts.add(e.value);
        return ts;
    }

    private ArrayList<Entry> wrap(List<T> list) {
        ArrayList<Entry> res = new ArrayList<>();
        for (T t : list) res.add(new Entry(t));
        return res;
    }

    private class Entry {
        final T value;
        final IdentityHashMap<T, Boolean> toSkip;
        int weight;

        private Entry(T t) {
            value = t;
            toSkip = new IdentityHashMap<>();
            weight = 0;
        }

        private void addToSkip(Entry t) {
            toSkip.put(t.value, true);
        }

        private void cancelSkip(Entry e) {
            toSkip.remove(e.value);
        }

        private boolean shouldSkip(Entry t) {
            return toSkip.containsKey(t.value);
        }
    }
}
