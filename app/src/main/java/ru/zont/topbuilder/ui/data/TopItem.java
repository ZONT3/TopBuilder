package ru.zont.topbuilder.ui.data;

import android.os.Parcel;
import android.os.Parcelable;

public class TopItem implements Parcelable {
    private String title;
    private String image;

    public TopItem(String title, String image) {
        this.title = title;
        this.image = image;
    }

    protected TopItem(Parcel in) {
        title = in.readString();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TopItem> CREATOR = new Creator<TopItem>() {
        @Override
        public TopItem createFromParcel(Parcel in) {
            return new TopItem(in);
        }

        @Override
        public TopItem[] newArray(int size) {
            return new TopItem[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
