package ru.zont.topbuilder.core;

import java.util.ArrayDeque;
import java.util.IdentityHashMap;

/**
 * Базовая реализация некоторых методов интерфейса {@link TopBuilder}
 */
public abstract class BasicTopBuilder<T> implements TopBuilder<T> {

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

    // TODO extract code for next() - provideDecision(int) routine
}
