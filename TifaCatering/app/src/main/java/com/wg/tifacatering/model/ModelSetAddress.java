package com.wg.tifacatering.model;

public class ModelSetAddress {
    public ModelSetAddress(String nama, String jalan, String rtrw, String keckab, String ongkir) {
        this.nama = nama;
        this.jalan = jalan;
        this.rtrw = rtrw;
        this.keckab = keckab;
        this.ongkir = ongkir;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJalan() {
        return jalan;
    }

    public void setJalan(String jalan) {
        this.jalan = jalan;
    }

    public String getRtrw() {
        return rtrw;
    }

    public void setRtrw(String rtrw) {
        this.rtrw = rtrw;
    }

    public String getKeckab() {
        return keckab;
    }

    public void setKeckab(String keckab) {
        this.keckab = keckab;
    }

    private String nama;
    private String jalan;
    private String rtrw;
    private String keckab;

    public String getOngkir() {
        return ongkir;
    }

    public void setOngkir(String ongkir) {
        this.ongkir = ongkir;
    }

    private String ongkir;


}
