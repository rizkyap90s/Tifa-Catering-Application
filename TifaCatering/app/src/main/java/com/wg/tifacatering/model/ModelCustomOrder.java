package com.wg.tifacatering.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelCustomOrder implements Parcelable {

    private String isiPesanan;
    private int hargaPesanan;


    public ModelCustomOrder(Parcel in) {
        isiPesanan = in.readString();
        hargaPesanan = in.readInt();
    }

    public ModelCustomOrder() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(isiPesanan);
        dest.writeInt(hargaPesanan);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelCustomOrder> CREATOR = new Creator<ModelCustomOrder>() {
        @Override
        public ModelCustomOrder createFromParcel(Parcel in) {
            return new ModelCustomOrder(in);
        }

        @Override
        public ModelCustomOrder[] newArray(int size) {
            return new ModelCustomOrder[size];
        }
    };

    public String getIsiPesanan() {
        return isiPesanan;
    }

    public void setIsiPesanan(String isiPesanan) {
        this.isiPesanan = isiPesanan;
    }

    public int getHargaPesanan() {
        return hargaPesanan;
    }

    public void setHargaPesanan(int hargaPesanan) {
        this.hargaPesanan = hargaPesanan;
    }
}
