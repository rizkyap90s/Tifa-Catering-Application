package com.wg.tifaadmin.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelMenu implements Parcelable {

    private int id_paket;
    private String nama_paket;
    private String grup_paket;
    private String isi_paket;
    private int harga_paket;
    private String image_paket;

    public ModelMenu() {
    }

    protected ModelMenu(Parcel in) {
        id_paket = Integer.parseInt(in.readString());
        nama_paket = in.readString();
        grup_paket = in.readString();
        isi_paket = in.readString();
        harga_paket = Integer.parseInt(in.readString());
        image_paket = in.readString();
    }

    public static final Creator<ModelMenu> CREATOR = new Creator<ModelMenu>() {
        @Override
        public ModelMenu createFromParcel(Parcel in) {
            return new ModelMenu(in);
        }

        @Override
        public ModelMenu[] newArray(int size) {
            return new ModelMenu[size];
        }
    };

    public int getId_paket() {
        return id_paket;
    }

    public void setId_paket(int id_paket) {
        this.id_paket = id_paket;
    }

    public String getNama_paket() {
        return nama_paket;
    }

    public void setNama_paket(String nama_paket) {
        this.nama_paket = nama_paket;
    }

    public String getGrup_paket() {
        return grup_paket;
    }

    public void setGrup_paket(String grup_paket) {
        this.grup_paket = grup_paket;
    }

    public String getIsi_paket() {
        return isi_paket;
    }

    public void setIsi_paket(String isi_paket) {
        this.isi_paket = isi_paket;
    }

    public int getHarga_paket() {
        return harga_paket;
    }

    public void setHarga_paket(int harga_paket) {
        this.harga_paket = harga_paket;
    }

    public String getImage_paket() {
        return image_paket;
    }

    public void setImage_paket(String image_paket) {
        this.image_paket = image_paket;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(String.valueOf(id_paket));
        dest.writeString(nama_paket);
        dest.writeString(grup_paket);
        dest.writeString(isi_paket);
        dest.writeString(String.valueOf(harga_paket));
        dest.writeString(image_paket);
    }
}
