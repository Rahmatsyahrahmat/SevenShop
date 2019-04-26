package com.hackjam.sevenshop.activity;

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.hackjam.sevenshop.R;
import com.hackjam.sevenshop.fragment.HomeFragment;
import com.hackjam.sevenshop.fragment.ListFragment;
import com.hackjam.sevenshop.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationMenu = findViewById(R.id.bnv_main);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_main_main,new HomeFragment()).commit();

        bottomNavigationMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                switch (menuItem.getItemId()){
                    case R.id.menu_main_home :
                        ft.replace(R.id.fl_main_main,new HomeFragment()).commit();
                        break;
                    case R.id.menu_main_list_pesanan:
                        ft.replace(R.id.fl_main_main,new ListFragment()).commit();
                        break;
                    case R.id.menu_main_profile:
                        ft.replace(R.id.fl_main_main,new ProfileFragment()).commit();
                        break;
                }
                return false;
            }
        });

    }
}
