package ru.zont.topbuilder.core.implement;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

import ru.zont.topbuilder.core.DecisionEntry;
import ru.zont.topbuilder.core.TopBuilder;

/**
 * Базовая реализация некоторых методов интерфейса {@link TopBuilder}
 */
public abstract class BasicTopBuilder<T> implements TopBuilder<T> {

    private final ArrayList<DecisionEntry<T>> history = new ArrayList<>();

    private final ArrayDeque<Runnable> undoStack = new ArrayDeque<>();

    @Override
    public void undo() {
        final Runnable todo = undoStack.pollLast();
        if (todo == null) return;
        todo.run();
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

    protected List<DecisionEntry<T>> cutHistory(int indexFromEnd) {
        final int size = history.size();
        return new ArrayList<>(history.subList(size - indexFromEnd, size));
    }

    protected void trimHistory(int count) {
        final int size = history.size();
        history.subList(size - count, size).clear();
    }

    // TODO extract code for next() - provideDecision(int) routine
}
