package ru.zont.topbuilder.core.implement;

import java.util.*;

import ru.zont.topbuilder.core.TopResult;
import ru.zont.topbuilder.core.TrackableProgress;

public class EloTopBuilder<T> extends BasicTopBuilder<T> implements TrackableProgress {

    private final IdentityHashMap<T, Integer> ratingMap = new IdentityHashMap<>();
    private final List<Pair<T, T>> pairs = new ArrayList<>();

    private final int decisionTotal;
    private int checked = 0;

    private Pair<T, T> currPair;

    public EloTopBuilder(List<T> tifor) {
        for (T t: tifor) ratingMap.put(t, 400);

        int c = 0;
        Set<T> keySet = ratingMap.keySet();
        IdentityHashMap<Object, Boolean> check = new IdentityHashMap<>();
        for (T t: keySet) {
            for (T d: keySet) {
                if (check.containsKey(d)) continue;
                if (t == d) continue;
                pairs.add(new Pair<>(t, d));
                c++;
            }
            check.put(t, true);
        }
        decisionTotal = c;
    }

    @Override
    public void next(Supplier<T> supplier) {
        if (!hasNext()) return;
        supplier.provide(currPair.left, currPair.right);
        acceptProgress(checked + 1, decisionTotal);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void provideDecision(int decision) {
        T a = currPair.left;
        T b = currPair.right;

        final int aRat = ratingMap.get(a);
        final int bRat = ratingMap.get(b);

        final int aK = eloCoefficient(aRat);
        final int bK = eloCoefficient(bRat);

        final double aExpect = expectedRating(aRat, bRat);
        final double bExpect = expectedRating(bRat, aRat);

        final double deltaA = aK * ((decision < 0 ? 1 : decision > 0 ? 0 : 0.5) - aExpect);
        final double deltaB = bK * ((decision > 0 ? 1 : decision < 0 ? 0 : 0.5) - bExpect);

        ratingMap.put(currPair.left, (int) (aRat + deltaA));
        ratingMap.put(currPair.right, (int) (bRat + deltaB));

        this.currPair.isCheck = true;
        checked++;

        final Pair<T, T> finalPair = this.currPair;
        addUndoAction(() -> {
            ratingMap.put(finalPair.left, aRat);
            ratingMap.put(finalPair.right, bRat);
            finalPair.isCheck = false;

            checked--;
        });
    }

    @Override
    public boolean hasNext() {
        findNext();
        return currPair != null;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public TopResult<T> getResults() {
        ArrayList<T> list = new ArrayList<>(ratingMap.size());
        list.addAll(ratingMap.keySet());
        Collections.sort(list, (o1, o2) -> ratingMap.get(o2) - ratingMap.get(o1));
        TopResult<T> topResult = new TopResult<>();
        for (int i = 0; i < list.size(); i++)
            topResult.put(i + 1, list.get(i));

        return topResult;
    }

    private void findNext() {
        currPair = null;
        for (Pair<T, T> pair: pairs) {
            if (!pair.isCheck) {
                currPair = pair;
                break;
            }
        }
    }

    private int eloCoefficient(int r) {
        if (r == 400) {
            return 40;
        } else if (r < 2400) {
            return 20;
        } else {
            return 10;
        }
    }

    private double expectedRating(int aR, int bR) {
        return 1 / (1 + Math.pow(10, ((bR - aR) / 400.)));
    }

    public IdentityHashMap<T, Integer> getRatingMap() {
        return ratingMap;
    }

    private static class Pair<T, D> {
        public T left;
        public D right;
        public boolean isCheck = false;

        public Pair(T t, D d) {
            left = t;
            right = d;
        }
    }
}