package ru.zont.topbuilder.core.implement;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import ru.zont.topbuilder.core.DecisionEntry;
import ru.zont.topbuilder.core.TopBuilder;

/**
 * Базовая реализация некоторых методов интерфейса {@link TopBuilder}
 */
public abstract class BasicTopBuilder<T> implements TopBuilder<T> {

    private boolean needRebuild = false;

    private IdentityHashMap<T, IdentityHashMap<T, Integer>> autoProvide = null;

    private final ArrayList<DecisionEntry<T>> history = new ArrayList<>();

    private final ArrayDeque<Runnable> undoStack = new ArrayDeque<>();

    @Override
    public void undo() {
        final Runnable todo = undoStack.pollLast();
        if (todo == null) return;
        todo.run();
        history.remove(history.size()-1);
    }

    /**
     * Добавить действие, которое должно быть выполнено при попытке отмены
     * <p>
     * <strong>CONTRACT:</strong>
     * Должно быть вызвано при каждом вызове метода {@link TopBuilder#provideDecision(int)}.
     * В {@link Runnable} должен быть код, который отменит данный выбор.
     * </p>
     *
     * <p>
     * Данная реализация подразумевает каждый подряд идущий
     * вызов {@link TopBuilder#undo()} друг-за-другом
     * в обратном порядке вызванных ранее методов {@link TopBuilder#provideDecision(int)}.
     * </p>
     * @param undo {@link Runnable}, который будет вызван для отмены текущего выбора.
     */
    protected void addUndoAction(Runnable undo) {
        undoStack.add(undo);
    }

    protected void addHistoryEntry(DecisionEntry<T> entry) {
        history.add(entry);
    }

    @Override
    public List<DecisionEntry<T>> getHistory() {
        return history;
    }

    public boolean changeDecision(int i, int newDecision) {
        if (i > history.size()) throw new IllegalArgumentException("Out of range");
        needRebuild = false;

        final int trimSize = history.size() - i;
        final List<DecisionEntry<T>> later = cutHistory(trimSize);
        undo(trimSize);

        provideDecision(newDecision);
        for (int j = 1; j < later.size(); j++) {
            if (needRebuild) return false;
            final DecisionEntry<T> curr = later.get(j);
            next((lhs, rhs) -> {
                if (curr.getLhs() != lhs || curr.getRhs() != rhs) {
                    needRebuild = true;
                    autoProvide = buildAutoProvide(later);
                } else {
                    provideDecision(curr.getDecision());
                }
            });
        }
        return true;
    }

    public boolean tryAutoDecision() {
        if (autoProvide == null)
            return false;

        needRebuild = true;
        next((lhs, rhs) -> {
            final IdentityHashMap<T, Integer> conn = autoProvide.get(rhs);
            final Integer i = conn != null ? conn.get(lhs) : null;
            if (i != null) {
                provideDecision(i);
                needRebuild = false;
            }
        });

        return !needRebuild;
    }

    private IdentityHashMap<T, IdentityHashMap<T, Integer>> buildAutoProvide(List<DecisionEntry<T>> list) {
        IdentityHashMap<T, IdentityHashMap<T, Integer>> res = new IdentityHashMap<>();
        for (DecisionEntry<T> e: list) {
            final T rhs = e.getRhs();
            final T lhs = e.getLhs();
            for (T t: Arrays.asList(lhs, rhs)) {
                IdentityHashMap<T, Integer> connect = res.get(t);
                if (connect == null) connect = new IdentityHashMap<>();
                final boolean isLhs = t == lhs;
                connect.put(
                        isLhs ? rhs : lhs,
                        isLhs ? e.getDecision() * -1 : e.getDecision()
                );
            }
        }
        return res;
    }

    private List<DecisionEntry<T>> cutHistory(int indexFromEnd) {
        final int size = history.size();
        return new ArrayList<>(history.subList(size - indexFromEnd + 1, size));
    }

    // TODO extract code for next() - provideDecision(int) routine
}
