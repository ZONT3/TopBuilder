package ru.zont.topbuilder.core.implement;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;

import ru.zont.topbuilder.core.TopResult;
import ru.zont.topbuilder.core.TrackableProgress;

public class WeightTopBuilder<T> extends BasicTopBuilder<T> implements TrackableProgress {
    private final ArrayList<Entry> list;

    private Entry currLhs;
    private Entry currRhs;
    private boolean validation = false;

    private final int decisionTotal;
    private int checked = 0;

    public WeightTopBuilder(List<T> list) {
        this.list = wrap(list);
        int c = 0;
        for (int i = 1; i < list.size(); i++) c += i;
        decisionTotal = c;
    }

    @Override
    public void next(Supplier<T> supplier) {
        if (!hasNext()) return;
        supplier.provide(currLhs.value, currRhs.value);
        acceptProgress(checked + 1, decisionTotal);
    }

    @Override
    public void provideDecision(int weight) {
        if (!validation) throw new IllegalStateException("Choice wasn't instanced");
        if (!hasNext()) throw new IllegalStateException("Top is already built");

        final Entry mCurrLhs = this.currLhs;
        final Entry mCurrRhs = this.currRhs;
        final Entry applyTo = weight > 0 ? mCurrRhs : mCurrLhs;

        mCurrRhs.setRelation(mCurrLhs, weight);
        mCurrLhs.setRelation(mCurrRhs, -weight);

        final int abs = Math.abs(weight);
        applyTo.weight += abs;

        checked++;

        validation = false;

        addHistoryEntry(new RatingDecisionEntry<>(
                mCurrLhs.value,
                mCurrRhs.value,
                weight,
                weight >= 0 ? 0 : abs,
                weight <= 0 ? 0 : abs,
                mCurrLhs.weight,
                mCurrRhs.weight
        ));

        addUndoAction(() -> {
            mCurrRhs.cancelRelation(mCurrLhs);
            mCurrLhs.cancelRelation(mCurrRhs);
            applyTo.weight -= abs;
            currLhs = mCurrLhs;
            currRhs = mCurrRhs;
            validation = true;

            checked--;
        });
    }

    @Override
    public boolean hasNext() {
        validate();
        return (currLhs != null && currRhs != null);
    }

    @Override
    public TopResult<T> getResults() {
        ArrayList<Entry> list = this.list;
        if (hasNext()) {
            list = new ArrayList<>(list);
            Collections.sort(list, (l, r) -> r.weight - l.weight);
        }

        TopResult<T> res = new TopResult<>();
        int place = 1;
        Entry prev = null;
        for (Entry entry : list) {
            if (prev != null && entry.isLesserThan(prev))
                place++;
            res.put(place, entry.value);
            prev = entry;
        }
        return res;
    }

    private void findNext() {
        currLhs = null;
        currRhs = null;

        final int n = list.size();
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                Entry lhs = list.get(j);
                Entry rhs = list.get(j + 1);
                if (lhs.isLesserThan(rhs)) {
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

    private ArrayList<Entry> wrap(List<T> list) {
        ArrayList<Entry> res = new ArrayList<>();
        for (T t : list) res.add(new Entry(t));
        return res;
    }

    private class Entry {
        final T value;
        final IdentityHashMap<T, Integer> relatives;
        int weight;

        private Entry(T t) {
            value = t;
            relatives = new IdentityHashMap<>();
            weight = 0;
        }

        private void cancelRelation(Entry e) {
            relatives.remove(e.value);
        }

        private void setRelation(Entry e, int r) {
            relatives.put(e.value, r);
        }

        private boolean shouldSkip(Entry t) {
            final Integer i = relatives.get(t.value);
            return i != null;
        }

        public boolean isLesserThan(Entry rhs) {
            if (weight < rhs.weight) return true;
            if (weight > rhs.weight) return false;

            final Integer i = relatives.get(rhs.value);
            return i != null && i < 0;
        }

        @NonNull
        @Override
        public String toString() {
            return String.format("%s (%s)", value.toString(), weight);
        }
    }
}
