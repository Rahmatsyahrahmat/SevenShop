package com.hackjam.sevenshop.fragment;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackjam.sevenshop.R;
import com.hackjam.sevenshop.adapter.KategoriAdapter;
import com.hackjam.sevenshop.adapter.ProdukBaruAdapter;
import com.hackjam.sevenshop.adapter.SliderAdapter;
import com.hackjam.sevenshop.model.Kategori;
import com.hackjam.sevenshop.model.itemProduk;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private View view;

    private RecyclerView rvKatgeori, rvProduk;

    private ProdukBaruAdapter produkAdapter;
    private KategoriAdapter kategoriAdapter;

    private SearchView searchView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        rvKatgeori = view.findViewById(R.id.rv_home_kategori);
        rvProduk = view.findViewById(R.id.rv_home_produk);
        searchView = view.findViewById(R.id.sv_home_home);
        rvKatgeori.setLayoutManager(new GridLayoutManager(getContext(),3));
        rvProduk.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<Kategori> kategoris = new ArrayList<>();
        kategoris.add(new Kategori("Laptop",R.drawable.laptop));
        kategoris.add(new Kategori("Handphone",R.drawable.handphone));
        kategoris.add(new Kategori("Kamera",R.drawable.kamera));
        kategoris.add(new Kategori("Otomotif",R.drawable.otomotif));
        kategoris.add(new Kategori("Fashion",R.drawable.fashion));
        kategoris.add(new Kategori("Komputer",R.drawable.komputer));


        kategoriAdapter = new KategoriAdapter(getContext(),kategoris);

        rvKatgeori.setAdapter(kategoriAdapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Produk").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<itemProduk> itemProduks = new ArrayList<>();
                for (DataSnapshot d:dataSnapshot.getChildren()) {
                    String id = d.getKey(),
                            nama = d.child("nama").getValue().toString(),
                            hargaAwal = d.child("hargaAwal").getValue().toString(),
                            hargaPotongan = d.child("hargaPotongan").getValue().toString(),
                            kategori = d.child("kategori").getValue().toString(),
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
                produkAdapter = new ProdukBaruAdapter(getContext(),itemProduks);
                rvProduk.setAdapter(produkAdapter);



                // listening to search query text change
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        // filter recycler view when query submitted
                        produkAdapter.getFilter().filter(query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String query) {
                        // filter recycler view when text is changed
                        produkAdapter.getFilter().filter(query);
                        return false;
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
        }

    }


