package ru.zont.topbuilder.core;

import java.util.ArrayDeque;

public abstract class BasicTopBuilder<T> implements TopBuilder<T> {

    private final ArrayDeque<Runnable> undoStack = new ArrayDeque<>();

    @Override
    public void undo() {
        final Runnable todo = undoStack.pop();
        todo.run();
    }

    protected void addUndoAction(Runnable undo) {
        undoStack.add(undo);
    }
}
