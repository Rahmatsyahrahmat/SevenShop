package com.hackjam.sevenshop.model;

import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;

public class itemProduk implements Serializable {
    private String nama, hargaAwal, hargaPotongan, deskripsi;
    private int jumlahJoin, maxJoin;

    public itemProduk(String nama, String hargaAwal, String hargaPotongan, String deskripsi, int jumlahJoin, int maxJoin) {
        this.nama = nama;
        this.hargaAwal = hargaAwal;
        this.hargaPotongan = hargaPotongan;
        this.deskripsi = deskripsi;
        this.jumlahJoin = jumlahJoin;
        this.maxJoin = maxJoin;
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
