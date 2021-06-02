package com.wg.tifacatering.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelHistory implements Parcelable {
    private String alamat_kirim;
    private int harga_paket;
    private String isi_paket;
    private String jenis_paket;
    private int jumlah_pesan;
    private String nama_paket;
    private String status;
    private String tanggal_kirim;
    private String waktu_kirim;
    private int minimal_pesan;
    private int total_bayar;
    private String nama_customer;
    private String nomor_customer;



    public ModelHistory() {
    }

    public String getAlamat_kirim() {
        return alamat_kirim;
    }

    public void setAlamat_kirim(String alamat_kirim) {
        this.alamat_kirim = alamat_kirim;
    }

    public int getHarga_paket() {
        return harga_paket;
    }

    public void setHarga_paket(int harga_paket) {
        this.harga_paket = harga_paket;
    }

    public String getIsi_paket() {
        return isi_paket;
    }

    public void setIsi_paket(String isi_paket) {
        this.isi_paket = isi_paket;
    }

    public String getJenis_paket() {
        return jenis_paket;
    }

    public void setJenis_paket(String jenis_paket) {
        this.jenis_paket = jenis_paket;
    }

    public int getJumlah_pesan() {
        return jumlah_pesan;
    }

    public void setJumlah_pesan(int jumlah_pesan) {
        this.jumlah_pesan = jumlah_pesan;
    }

    public String getNama_paket() {
        return nama_paket;
    }

    public void setNama_paket(String nama_paket) {
        this.nama_paket = nama_paket;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggal_kirim() {
        return tanggal_kirim;
    }

    public void setTanggal_kirim(String tanggal_kirim) {
        this.tanggal_kirim = tanggal_kirim;
    }

    public String getWaktu_kirim() {
        return waktu_kirim;
    }

    public void setWaktu_kirim(String waktu_kirim) {
        this.waktu_kirim = waktu_kirim;
    }

    public int getMinimal_pesan() {
        return minimal_pesan;
    }

    public void setMinimal_pesan(int minimal_pesan) {
        this.minimal_pesan = minimal_pesan;
    }

    public int getTotal_bayar() {
        return total_bayar;
    }

    public void setTotal_bayar(int total_bayar) {
        this.total_bayar = total_bayar;
    }

    public String getNama_customer() {
        return nama_customer;
    }

    public void setNama_customer(String nama_customer) {
        this.nama_customer = nama_customer;
    }

    public String getNomor_customer() {
        return nomor_customer;
    }

    public void setNomor_customer(String nomor_customer) {
        this.nomor_customer = nomor_customer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.alamat_kirim);
        dest.writeInt(this.harga_paket);
        dest.writeString(this.isi_paket);
        dest.writeString(this.jenis_paket);
        dest.writeInt(this.jumlah_pesan);
        dest.writeString(this.nama_paket);
        dest.writeString(this.status);
        dest.writeString(this.tanggal_kirim);
        dest.writeString(this.waktu_kirim);
        dest.writeInt(this.minimal_pesan);
        dest.writeInt(this.total_bayar);
        dest.writeString(this.nama_customer);
        dest.writeString(this.nomor_customer);
    }

    protected ModelHistory(Parcel in) {
        this.alamat_kirim = in.readString();
        this.harga_paket = in.readInt();
        this.isi_paket = in.readString();
        this.jenis_paket = in.readString();
        this.jumlah_pesan = in.readInt();
        this.nama_paket = in.readString();
        this.status = in.readString();
        this.tanggal_kirim = in.readString();
        this.waktu_kirim = in.readString();
        this.minimal_pesan = in.readInt();
        this.total_bayar = in.readInt();
        this.nama_customer = in.readString();
        this.nomor_customer = in.readString();
    }

    public static final Creator<ModelHistory> CREATOR = new Creator<ModelHistory>() {
        @Override
        public ModelHistory createFromParcel(Parcel source) {
            return new ModelHistory(source);
        }

        @Override
        public ModelHistory[] newArray(int size) {
            return new ModelHistory[size];
        }
    };
}
