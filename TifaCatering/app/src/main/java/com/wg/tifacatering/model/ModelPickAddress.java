package com.wg.tifacatering.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelPickAddress implements Parcelable {

    private String jalan;
    private String kec;
    private String kab;
    private String nama;
    private String note;
    private String rtrw;
    private String ongkir;

    protected ModelPickAddress(Parcel in) {
        jalan = in.readString();
        kec = in.readString();
        kab = in.readString();
        nama = in.readString();
        note = in.readString();
        rtrw = in.readString();
        ongkir = in.readString();
    }

    public ModelPickAddress() {
    }

    public ModelPickAddress(String jalan, String kec, String kab, String nama, String note, String rtrw, String ongkir) {
        this.jalan = jalan;
        this.kec = kec;
        this.kab = kab;
        this.nama = nama;
        this.note = note;
        this.rtrw = rtrw;
        this.ongkir = ongkir;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(jalan);
        dest.writeString(kec);
        dest.writeString(kab);
        dest.writeString(nama);
        dest.writeString(note);
        dest.writeString(rtrw);
        dest.writeString(ongkir);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelPickAddress> CREATOR = new Creator<ModelPickAddress>() {
        @Override
        public ModelPickAddress createFromParcel(Parcel in) {
            return new ModelPickAddress(in);
        }

        @Override
        public ModelPickAddress[] newArray(int size) {
            return new ModelPickAddress[size];
        }
    };

    public String getJalan() {
        return jalan;
    }

    public void setJalan(String jalan) {
        this.jalan = jalan;
    }

    public String getKec() {
        return kec;
    }

    public void setKec(String kec) {
        this.kec = kec;
    }

    public String getKab() {
        return kab;
    }

    public void setKab(String kab) {
        this.kab = kab;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRtrw() {
        return rtrw;
    }

    public void setRtrw(String rtrw) {
        this.rtrw = rtrw;
    }

    public String getOngkir() {
        return ongkir;
    }

    public void setOngkir(String ongkir) {
        this.ongkir = ongkir;
    }
}
