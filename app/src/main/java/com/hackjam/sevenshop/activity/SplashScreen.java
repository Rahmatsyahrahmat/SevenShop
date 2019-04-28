package com.hackjam.sevenshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hackjam.sevenshop.R;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spalshscreen);

        //startActivity(new Intent(SplashScreen.this,LoginActivity.class));
    }
}
