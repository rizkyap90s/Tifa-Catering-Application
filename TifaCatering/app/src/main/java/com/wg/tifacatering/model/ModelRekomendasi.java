package com.wg.tifacatering.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelRekomendasi implements Parcelable {
    private int id_paket;
    private String nama_paket;
    private String grup_paket;
    private String isi_paket;
    private int harga_paket;
    private String image_paket;
    private int minimal_pesan;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id_paket);
        dest.writeString(this.nama_paket);
        dest.writeString(this.grup_paket);
        dest.writeString(this.isi_paket);
        dest.writeInt(this.harga_paket);
        dest.writeString(this.image_paket);
        dest.writeInt(this.minimal_pesan);
    }

    public ModelRekomendasi() {
    }

    protected ModelRekomendasi(Parcel in) {
        this.id_paket = in.readInt();
        this.nama_paket = in.readString();
        this.grup_paket = in.readString();
        this.isi_paket = in.readString();
        this.harga_paket = in.readInt();
        this.image_paket = in.readString();
        this.minimal_pesan = in.readInt();
    }

    public static final Parcelable.Creator<ModelRekomendasi> CREATOR = new Parcelable.Creator<ModelRekomendasi>() {
        @Override
        public ModelRekomendasi createFromParcel(Parcel source) {
            return new ModelRekomendasi(source);
        }

        @Override
        public ModelRekomendasi[] newArray(int size) {
            return new ModelRekomendasi[size];
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

    public int getMinimal_pesan() {
        return minimal_pesan;
    }

    public void setMinimal_pesan(int minimal_pesan) {
        this.minimal_pesan = minimal_pesan;
    }
}
