package ru.zont.topbuilder.ui.data.info;

import android.content.Context;

import ru.zont.topbuilder.R;
import ru.zont.topbuilder.core.WeightTopBuilder;
import ru.zont.topbuilder.ui.data.TopBuilderInfo;
import ru.zont.topbuilder.ui.data.TopItem;
import ru.zont.topbuilder.ui.data.TopList;

public class WeightTopBuilderInfo extends TopBuilderInfo<WeightTopBuilder<TopItem>> {

    @Override
    public String getTitle(Context context) {
        return context.getResources().getString(R.string.info_weight);
    }

    @Override
    public String getDescription(Context context) {
        return "";
    }

    @Override
    public int getImage(Context context) {
        return R.drawable.ic_launcher_background;
    }

    @Override
    public Formula maxSteps(Context context) {
        return null;
    }

    @Override
    public WeightTopBuilder<TopItem> newBuilderInstance(Context context, TopList items) {
        return new WeightTopBuilder<>(items);
    }
}
