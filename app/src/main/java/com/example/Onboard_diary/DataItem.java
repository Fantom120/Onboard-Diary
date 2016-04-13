package com.example.Onboard_diary;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Calendar;


public class DataItem implements Parcelable, Comparable{

    private long _id;
    private String theme;
    private String description;
    private long date;

    public DataItem() {

    }

    public DataItem(int _id, String theme, String description) {
        this._id = _id;
        this.theme = theme;
        this.description = description;

    }

    public DataItem(long _id, String theme, String description, Long date) {
        this._id = _id;
        this.theme = theme;
        this.description = description;
        this.date = date;
    }

    public DataItem(String theme, String description) {
        this.theme = theme;
        this.description = description;

    }

    public DataItem(long date, String theme, String description) {
        this.date = date;
        this.theme = theme;
        this.description = description;
    }

    public DataItem(String theme) {
        this.theme = theme;


    }

    public DataItem( Calendar dt1) {
    }

    protected DataItem(Parcel in) {
        _id = in.readLong();
        theme = in.readString();
        description = in.readString();
        date = in.readLong();
    }



    public long get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(long date) {

        this.date = date;

    }
    public long getDate() {
         return date;

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataItem dataItem = (DataItem) o;

        return _id == dataItem._id;

    }

    @Override
    public int hashCode() {
        return (int) (_id ^ (_id >>> 32));
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeString(theme);
        dest.writeString(description);
        dest.writeLong(date);
    }
    public static final Parcelable.Creator<DataItem> CREATOR = new Parcelable.Creator<DataItem>() {   // Статический метод с помощью которого создаем обьект
        public DataItem createFromParcel(Parcel in) {
            return new DataItem(in);
        }

        public DataItem[] newArray(int size) {
            return new DataItem[size];
        }
    };

    @Override
    public int compareTo(@NonNull Object another) {
        DataItem tmp = (DataItem)another;
        if(this.date > tmp.date)
        {
      /* текущее меньше полученного */
            return -1;
        }
        else if(this.date < tmp.date)
        {
      /* текущее больше полученного */
            return 1;
        }
    /* текущее равно полученному */
        return 0;
    }
}

