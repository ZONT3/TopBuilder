package ru.zont.topbuilder.core;

import java.util.ArrayDeque;

public abstract class BasicTopBuilder<T> implements TopBuilder<T> {

    private final ArrayDeque<Runnable> undoStack = new ArrayDeque<>();

    @Override
    public void undo() {
        final Runnable todo = undoStack.pollLast();
        if (todo == null) return;
        todo.run();
    }

    protected void addUndoAction(Runnable undo) {
        undoStack.add(undo);
    }

    // TODO extract code for next() - provideDecision(int) routine
}
