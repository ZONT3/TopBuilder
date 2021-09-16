package ru.zont.topbuilder.core;

import java.util.List;

public interface TopBuilder<T> {

    /**
     * Выдает новую пару для решения пользователем
     * @param supplier Функция, в которой пользователь будет получать пару объектов
     */
    void next(Supplier<T> supplier);

    /**
     * <p>
     * Получить решение от пользователя, основанное на заданном ранее выборе.
     * </p>
     *
     * <p>
     * <strong>CONTRACT:</strong> пользователь должен вызввать этот метод только после
     * метода {@link TopBuilder#next(Supplier)}.
     * Соответственно, выбор должен быть на основе последнего вызова указанного выше метода.
     * </p>
     * @param decision Решение. Значения меньше нуля означают выбор в пользу левого варианта,
     *                 больше - в пользу правого.
     *                 Значение ноль - решение не может быть принято. В таком случае
     *                 рекомендуется больше не предлагать предоставленный выбор
     */
    void provideDecision(int decision);

    /**
     * Откат последних изменений
     */
    void undo();

    /**
     * Откат последних {@code n} изменений
     * @param count количество изменений для отката
     */
    default void undo(int count) {
        for (int i = 0; i < count; i++)
            undo();
    }

    /**
     * Может ли метод {@link TopBuilder#next(Supplier)} предоставить выбор.
     * Подразусевается, что при {@code false} - топ-лист уже построен
     * @return требуется ли еще один выбор
     */
    boolean hasNext();

    /**
     * Получить готовый топ-лист. Само построение топа не обязательно должно быть выполнено
     * ({@link TopBuilder#hasNext()} не обязан возвращать {@code false}).
     * Конкретное поведения остается за имплементацией.
     * @return Список с результатами
     */
    TopResult<T> getResults();

    List<DecisionEntry<T>> getHistory();

    interface Supplier<T> {
        void provide(T lhs, T rhs);
    }

    interface BiConsumer<T, U> {
        void accept(T t, U u);
    }

    interface Consumer<T> {
        void accept(T t);
    }
}
