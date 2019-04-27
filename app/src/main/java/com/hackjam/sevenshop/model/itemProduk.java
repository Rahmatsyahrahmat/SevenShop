package com.hackjam.sevenshop.model;

import android.support.v7.app.AppCompatActivity;

public class itemProduk extends AppCompatActivity {

    public itemProduk(int mImageResource,String namabarang,int hargalama,int hargamurah){
        this.mImageResource=mImageResource;
        this.namabarang=namabarang;
        this.hargalama=hargalama;
        this.hargamurah=hargamurah;
    }

    private int mImageResource;
    private String namabarang;
    private int hargamurah,hargalama;

    public int getmImageResource() {
        return mImageResource;
    }

    public void setmImageResource(int mImageResource) {
        this.mImageResource = mImageResource;
    }

    public String getNamabarang() {
        return namabarang;
    }

    public void setNamabarang(String namabarang) {
        this.namabarang = namabarang;
    }

    public int getHargamurah() {
        return hargamurah;
    }

    public void setHargamurah(int hargamurah) {
        this.hargamurah = hargamurah;
    }

    public int getHargalama() {
        return hargalama;
    }

    public void setHargalama(int hargalama) {
        this.hargalama = hargalama;
    }

}
