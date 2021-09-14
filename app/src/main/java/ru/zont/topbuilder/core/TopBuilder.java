package ru.zont.topbuilder.core;

public interface TopBuilder<T> {
    void next(Supplier<T> supplier);

    void provideDecision(int decision);

    void undo();

    default void undo(int count) {
        for (int i = 0; i < count; i++)
            undo();
    }

    boolean hasNext();

    TopResult<T> getResults();

    interface Supplier<T> {
        void provide(T lhs, T rhs);
    }
}
