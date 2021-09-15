package ru.zont.topbuilder.ui.data;

import android.content.Context;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

import java.util.function.Function;

import ru.zont.topbuilder.core.TopBuilder;

/**
 * Info providing class
 * @param <T> Referred {@link TopBuilder} implementation class
 */
public abstract class TopBuilderInfo<T extends TopBuilder<? extends TopItem>> {
    public abstract String getTitle(Context context);

    public abstract String getDescription(Context context);

    public abstract @DrawableRes int getImage(Context context);

    public abstract Formula maxSteps(Context context);

    public abstract T newBuilderInstance(Context context, TopList items);

    protected interface Formula {
        Function<Integer, Float> getFunction();

        @NonNull
        String toString();
    }
}
