package com.hackjam.sevenshop.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackjam.sevenshop.R;
import com.hackjam.sevenshop.adapter.ProdukBaruAdapter;
import com.hackjam.sevenshop.model.Kategori;
import com.hackjam.sevenshop.model.itemProduk;

import java.util.ArrayList;

public class KategoriActivity extends AppCompatActivity {

    private TextView kategori;
    private RecyclerView recyclerView;
    private ProdukBaruAdapter produkAdapter;
    private String sKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        kategori = findViewById(R.id.tv_kategori);
        recyclerView = findViewById(R.id.rv_kategori);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sKategori = getIntent().getExtras().getString("Kategori");
        kategori.setText(sKategori);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Produk").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<itemProduk> itemProduks = new ArrayList<>();
                for (DataSnapshot d:dataSnapshot.getChildren()) {
                    String kategori = d.child("kategori").getValue().toString();
                    if (sKategori.equals(kategori)){
                        String id = d.getKey(),
                                nama = d.child("nama").getValue().toString(),
                                hargaAwal = d.child("hargaAwal").getValue().toString(),
                                hargaPotongan = d.child("hargaPotongan").getValue().toString(),
                                deskripsi = d.child("deskripsi").getValue().toString();
                        int jumlahJoin = Integer.parseInt(d.child("jumlahJoin").getValue().toString()),
                                maxJoin = Integer.parseInt(d.child("maxJoin").getValue().toString());
                        String [] link = new String[(int) d.child("linkGambar").getChildrenCount()];
                        for (int i = 0; i <link.length ; i++) {
                            link[i] = d.child("linkGambar").child(String.valueOf(i+1)).getValue().toString();
                        }
                        itemProduk itemProduk = new itemProduk(id,nama,kategori,hargaAwal,hargaPotongan,deskripsi,jumlahJoin,maxJoin,link);
                        itemProduks.add(itemProduk);
                    }

                }
                produkAdapter = new ProdukBaruAdapter(KategoriActivity.this,itemProduks);
                recyclerView.setAdapter(produkAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
