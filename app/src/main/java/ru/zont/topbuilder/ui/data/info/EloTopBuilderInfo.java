package ru.zont.topbuilder.ui.data.info;

import android.content.Context;

import ru.zont.topbuilder.R;
import ru.zont.topbuilder.core.implement.EloTopBuilder;
import ru.zont.topbuilder.ui.data.TopBuilderInfo;
import ru.zont.topbuilder.ui.data.TopItem;
import ru.zont.topbuilder.ui.data.TopList;

public class EloTopBuilderInfo extends TopBuilderInfo<EloTopBuilder<TopItem>> {
    @Override
    public String getTitle(Context context) {
        return context.getResources().getString(R.string.info_elo);
    }

    @Override
    public String getDescription(Context context) {
        return context.getResources().getString(R.string.info_elo_desc);
    }

    @Override
    public int getImage(Context context) {
        return R.drawable.ic_launcher_foreground;
    }

    @Override
    public Formula maxSteps(Context context) {
        return null;
    }

    @Override
    public EloTopBuilder<TopItem> newBuilderInstance(Context context, TopList items) {
        return new EloTopBuilder<>(items);
    }
}
