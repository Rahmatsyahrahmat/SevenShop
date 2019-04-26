package com.hackjam.sevenshop.fragment;


import android.os.Bundle;
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
        rvKatgeori.setLayoutManager(new GridLayoutManager(getContext(),3));
        rvProduk.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<Kategori> kategoris = new ArrayList<>();
        kategoris.add(new Kategori("Laptop",R.drawable.ic_home_24dp));
        kategoris.add(new Kategori("Handphone",R.drawable.ic_home_24dp));
        kategoris.add(new Kategori("Kamera",R.drawable.ic_home_24dp));
        kategoris.add(new Kategori("Otomotif",R.drawable.ic_home_24dp));
        kategoris.add(new Kategori("Fashion",R.drawable.ic_home_24dp));
        kategoris.add(new Kategori("Komputer",R.drawable.ic_home_24dp));

        ArrayList<itemProduk> produks = new ArrayList<>();
        produks.add(new itemProduk("skdjnsdjn","skdfnskfcsjdnc","fsnfskdfj","fsiuncweskucfne kuchjnfesidfcujskn",28,100));
        produks.add(new itemProduk("skdjnsdjn","skdfnskfcsjdnc","fsnfskdfj","fsiuncweskucfne kuchjnfesidfcujskn",28,100));
        produks.add(new itemProduk("skdjnsdjn","skdfnskfcsjdnc","fsnfskdfj","fsiuncweskucfne kuchjnfesidfcujskn",28,100));
        produks.add(new itemProduk("skdjnsdjn","skdfnskfcsjdnc","fsnfskdfj","fsiuncweskucfne kuchjnfesidfcujskn",28,100));
        produks.add(new itemProduk("skdjnsdjn","skdfnskfcsjdnc","fsnfskdfj","fsiuncweskucfne kuchjnfesidfcujskn",28,100));
        produks.add(new itemProduk("skdjnsdjn","skdfnskfcsjdnc","fsnfskdfj","fsiuncweskucfne kuchjnfesidfcujskn",28,100));
        produks.add(new itemProduk("skdjnsdjn","skdfnskfcsjdnc","fsnfskdfj","fsiuncweskucfne kuchjnfesidfcujskn",28,100));
        produks.add(new itemProduk("skdjnsdjn","skdfnskfcsjdnc","fsnfskdfj","fsiuncweskucfne kuchjnfesidfcujskn",28,100));
        produks.add(new itemProduk("skdjnsdjn","skdfnskfcsjdnc","fsnfskdfj","fsiuncweskucfne kuchjnfesidfcujskn",28,100));
        produks.add(new itemProduk("skdjnsdjn","skdfnskfcsjdnc","fsnfskdfj","fsiuncweskucfne kuchjnfesidfcujskn",28,100));
        produks.add(new itemProduk("skdjnsdjn","skdfnskfcsjdnc","fsnfskdfj","fsiuncweskucfne kuchjnfesidfcujskn",28,100));



        produkAdapter = new ProdukBaruAdapter(getContext(),produks);
        kategoriAdapter = new KategoriAdapter(getContext(),kategoris);

        rvProduk.setAdapter(produkAdapter);
        rvKatgeori.setAdapter(kategoriAdapter);

        return view;
        }

    }


