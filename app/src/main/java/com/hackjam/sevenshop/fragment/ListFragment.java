package com.hackjam.sevenshop.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackjam.sevenshop.R;
import com.hackjam.sevenshop.activity.KategoriActivity;
import com.hackjam.sevenshop.adapter.ProdukBaruAdapter;
import com.hackjam.sevenshop.model.itemProduk;

import java.util.ArrayList;

/**1
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private ProdukBaruAdapter produkBaruAdapter;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = rootView.findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String []list = dataSnapshot.child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("list").getValue().toString().split(",");
                ArrayList<itemProduk> itemProduks = new ArrayList<>();
                for (int i = 0; i <list.length ; i++) {
                    String id = dataSnapshot.child("Produk").child(list[i]).getKey(),
                            nama = dataSnapshot.child("Produk").child(list[i]).child("nama").getValue().toString(),
                            hargaAwal = dataSnapshot.child("Produk").child(list[i]).child("hargaAwal").getValue().toString(),
                            hargaPotongan = dataSnapshot.child("Produk").child(list[i]).child("hargaPotongan").getValue().toString(),
                            kategori = dataSnapshot.child("Produk").child(list[i]).child("kategori").getValue().toString(),
                            deskripsi = dataSnapshot.child("Produk").child(list[i]).child("deskripsi").getValue().toString();
                    int jumlahJoin = Integer.parseInt(dataSnapshot.child("Produk").child(list[i]).child("jumlahJoin").getValue().toString()),
                            maxJoin = Integer.parseInt(dataSnapshot.child("Produk").child(list[i]).child("maxJoin").getValue().toString());
                    String [] link = new String[(int) dataSnapshot.child("Produk").child(list[i]).child("linkGambar").getChildrenCount()];
                    for (int j = 0; j <link.length ; j++) {
                        link[j] = dataSnapshot.child("Produk").child(list[i]).child("linkGambar").child(String.valueOf(j+1)).getValue().toString();
                    }
                    itemProduk itemProduk = new itemProduk(id,nama,kategori,hargaAwal,hargaPotongan,deskripsi,jumlahJoin,maxJoin,link);
                    itemProduks.add(itemProduk);
                }
                produkBaruAdapter = new ProdukBaruAdapter(getContext(),itemProduks);
                recyclerView.setAdapter(produkBaruAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return rootView;
    }

}
