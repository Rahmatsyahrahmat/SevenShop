package com.hackjam.sevenshop.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.hackjam.sevenshop.R;
import com.hackjam.sevenshop.adapter.ProdukBaruAdapter;
import com.hackjam.sevenshop.adapter.SliderAdapter;
import com.hackjam.sevenshop.model.itemProduk;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ViewPager viewPager;
    SliderAdapter sliderAdapter;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    ProdukBaruAdapter produkBaruAdapter;

    List<itemProduk> produkitem = new ArrayList<>();
    ;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = view.findViewById(R.id.vp_slide_home);
        sliderAdapter = new SliderAdapter(getActivity());
        viewPager.setAdapter(sliderAdapter);
        sliderDotspanel = view.findViewById(R.id.ll_slide_home);
        dotscount = viewPager.getChildCount();
        dots = new ImageView[dotscount];

//        //recycler view
//        recyclerView = view.findViewById(R.id.produk_recycler_home);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        produkitem.add(new itemProduk(1,"Asus Pro",3999000,2000000));
//        produkitem.add(new itemProduk(1,"MacBook Pro",8999000,4000000));
//        produkitem.add(new itemProduk(1,"Samsung Pro",399000,1500000));
//
//        //recycler adapter
//        ProdukBaruAdapter adapter = new ProdukBaruAdapter(getActivity(),produkitem);
//
//        //set adapter into recycler
//        recyclerView.setAdapter(adapter);
        return view;
    }

    }

