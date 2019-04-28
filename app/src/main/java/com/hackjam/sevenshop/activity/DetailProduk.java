package com.hackjam.sevenshop.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hackjam.sevenshop.R;
import com.hackjam.sevenshop.model.itemProduk;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;


public class DetailProduk extends AppCompatActivity {

    private SliderLayout sliderLayout;

    private itemProduk itemProduk;

    private TextView nama, hargaLama, hargaPotongan, deskripsi;
    private Button mau;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);
        sliderLayout = findViewById(R.id.imageSlider);
        sliderLayout.setScrollTimeInSec(1); //set scroll delay in seconds :

        itemProduk = (itemProduk) getIntent().getExtras().getSerializable("Produk");
        nama = findViewById(R.id.tv_detailproduk_nama);
        hargaLama = findViewById(R.id.tv_detailproduk_hargalama);
        hargaPotongan = findViewById(R.id.tv_detailproduk_hargapotongan);
        deskripsi = findViewById(R.id.tv_detailproduk_deksripsi);
        mau = findViewById(R.id.btn_detailproduk_mau);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        nama.setText(itemProduk.getNama());
        hargaLama.setText(itemProduk.getHargaAwal());
        hargaPotongan.setText(itemProduk.getHargaPotongan());
        deskripsi.setText(itemProduk.getDeskripsi());

        setSliderViews();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                try {
                    final String[] list = {dataSnapshot.child("User").child(firebaseAuth.getCurrentUser().getUid()).child("list").getValue().toString()};
                    String []lists = list[0].split(",");
                    Log.i("hyhy",String.valueOf(lists[0]));
                    if (isJoined(lists)){
                        mau.setBackground(getResources().getDrawable(R.drawable.btn_white));
                        mau.setTextColor(getResources().getColor(R.color.colorPrimary));
                        Log.i("hyhy","a");
                        mau.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(DetailProduk.this,"Anda telah join",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else {
                        mau.setBackground(getResources().getDrawable(R.drawable.btn_green));
                        mau.setTextColor(getResources().getColor(R.color.secondaryLightColor));
                        Log.i("hyhy","b");
                        mau.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int jumlahJoin = Integer.parseInt(dataSnapshot.child("Produk").child(itemProduk.getId()).child("jumlahJoin").getValue().toString()) ;
                                databaseReference.child("Produk").child(itemProduk.getId()).child("jumlahJoin").setValue(jumlahJoin+1);
                                list[0] = list[0] +","+itemProduk.getId();
                                databaseReference.child("User").child(firebaseAuth.getCurrentUser().getUid()).child("list").setValue(list[0]);
                                finish();
                            }
                        });
                    }
                }catch (NullPointerException ex){
                    mau.setBackground(getResources().getDrawable(R.drawable.btn_green));
                    mau.setTextColor(getResources().getColor(R.color.secondaryLightColor));
                    Log.i("hyhy","c");
                    mau.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int jumlahJoin = Integer.parseInt(dataSnapshot.child("Produk").child(itemProduk.getId()).child("jumlahJoin").getValue().toString()) ;
                            databaseReference.child("Produk").child(itemProduk.getId()).child("jumlahJoin").setValue(jumlahJoin+1);
                            databaseReference.child("User").child(firebaseAuth.getCurrentUser().getUid()).child("list").setValue(itemProduk.getId());
                            finish();
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void setSliderViews() {



        for (int i = 0; i < itemProduk.getLinkGambar().length; i++) {

            DefaultSliderView sliderView = new DefaultSliderView(this);

            sliderView.setImageUrl(itemProduk.getLinkGambar()[i]);

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);


            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }
    private boolean isJoined(String []lists){
        boolean is = false;
        for (int i = 0; i <lists.length ; i++) {
            if (lists[i].equals(itemProduk.getId())){
                is = true;
            }
        }
        return is;
    }

}
