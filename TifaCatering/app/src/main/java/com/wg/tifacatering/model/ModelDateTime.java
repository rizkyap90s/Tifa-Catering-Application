package com.wg.tifacatering.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelDateTime implements Parcelable {
    private String date;
    private String time;

    public ModelDateTime() {
    }



    protected ModelDateTime(Parcel in) {
        date = in.readString();
        time = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(time);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelDateTime> CREATOR = new Creator<ModelDateTime>() {
        @Override
        public ModelDateTime createFromParcel(Parcel in) {
            return new ModelDateTime(in);
        }

        @Override
        public ModelDateTime[] newArray(int size) {
            return new ModelDateTime[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
