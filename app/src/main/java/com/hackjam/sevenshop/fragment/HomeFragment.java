package com.hackjam.sevenshop.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.hackjam.sevenshop.R;
import com.hackjam.sevenshop.activity.adapter.SliderAdapter;
import com.hackjam.sevenshop.model.itemProduk;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ArrayList<itemProduk> itemProduks = new ArrayList<>();
        itemProduks.add(new itemProduk(R.layout.item_produk_baru))

        viewPager = view.findViewById(R.id.vp_slide_home);
        sliderAdapter = new SliderAdapter(getActivity());
        viewPager.setAdapter(sliderAdapter);
        sliderDotspanel = view.findViewById(R.id.ll_slide_home);
        dotscount = viewPager.getChildCount();
        dots = new ImageView[dotscount];

        //progress bar
        progressBar = view.findViewById(R.id.pb_produk_item_produk_baru);
        progressBar.setProgress(0);
        progressBar.setMax(100);

        return view;
        }

    }

