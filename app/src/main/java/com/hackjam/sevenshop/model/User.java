package com.hackjam.sevenshop.model;

public class User {
    private String uid, nama, email, alamat, kecamatan, kota, provinsi, no_telp;

    public User(String uid, String nama, String email, String alamat, String kecamatan, String kota, String provinsi, String no_telp) {
        this.uid = uid;
        this.nama = nama;
        this.email = email;
        this.alamat = alamat;
        this.kecamatan = kecamatan;
        this.kota = kota;
        this.provinsi = provinsi;
        this.no_telp = no_telp;
    }

    public String getUid() {
        return uid;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public String getKota() {
        return kota;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public String getNo_telp() {
        return no_telp;
    }
}
