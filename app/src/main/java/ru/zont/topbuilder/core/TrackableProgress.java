package ru.zont.topbuilder.core;

public interface TrackableProgress {
    void setProgressListener(TopBuilder.BiConsumer<Integer, Integer> consumer);
}
