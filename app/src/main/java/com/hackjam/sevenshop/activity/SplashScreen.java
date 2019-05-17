package com.hackjam.sevenshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.hackjam.sevenshop.R;

public class SplashScreen extends AppCompatActivity {

    private ImageView logo1,logo2,logo3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo1 = findViewById(R.id.iv_logo1_ss);
        logo2 = findViewById(R.id.iv_logo2_ss);
        logo3 = findViewById(R.id.iv_logo3_ss);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.splashanimation);
        logo1.startAnimation(animation);
        logo2.startAnimation(animation);
        logo3.startAnimation(animation);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(4000);
                    Intent intent = new Intent(SplashScreen.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                super.run();
            }
        };
        thread.run();
    }
}
