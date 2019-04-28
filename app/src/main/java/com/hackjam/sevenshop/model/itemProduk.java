package com.hackjam.sevenshop.model;

import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;

public class itemProduk implements Serializable {
    private String id, nama, kategori, hargaAwal, hargaPotongan, deskripsi;
    private String[]linkGambar;
    private int jumlahJoin, maxJoin;

    public itemProduk(String id,String nama,String kategori, String hargaAwal, String hargaPotongan, String deskripsi, int jumlahJoin, int maxJoin, String []linkGambar) {
        this.id = id;
        this.nama = nama;
        this.kategori = kategori;
        this.hargaAwal = hargaAwal;
        this.hargaPotongan = hargaPotongan;
        this.deskripsi = deskripsi;
        this.jumlahJoin = jumlahJoin;
        this.maxJoin = maxJoin;
        this.linkGambar = linkGambar;
    }

    public String[] getLinkGambar() {
        return linkGambar;
    }

    public String getKategori() {
        return kategori;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getHargaAwal() {
        return hargaAwal;
    }

    public String getHargaPotongan() {
        return hargaPotongan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public int getJumlahJoin() {
        return jumlahJoin;
    }

    public int getMaxJoin() {
        return maxJoin;
    }
}
