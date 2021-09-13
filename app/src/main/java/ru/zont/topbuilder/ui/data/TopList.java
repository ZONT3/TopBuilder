package ru.zont.topbuilder.ui.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

public class TopList extends ArrayList<TopItem> implements Parcelable {

    public TopList() { }

    public TopList(@NonNull Collection<? extends TopItem> c) {
        super(c);
    }

    public TopList(int initialCapacity) {
        super(initialCapacity);
    }

    protected TopList(Parcel in) {
        in.readTypedList(this, TopItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TopList> CREATOR = new Creator<TopList>() {
        @Override
        public TopList createFromParcel(Parcel in) {
            return new TopList(in);
        }

        @Override
        public TopList[] newArray(int size) {
            return new TopList[size];
        }
    };
}
