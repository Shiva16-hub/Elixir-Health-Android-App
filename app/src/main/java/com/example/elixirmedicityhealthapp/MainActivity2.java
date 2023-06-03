package com.example.elixirmedicityhealthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity {
    BottomNavigationView bnView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bnView = findViewById(R.id.bottomNav);

        bnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.bottom_home){
                    loadFrag(new HomeFragment(), true);
                } else if (id == R.id.bottom_appoint) {
                    loadFrag(new AppointmentsFragment(), false);
                } else if (id == R.id.bottom_search) {
                    loadFrag(new SearchFragment(), false);
                } else if (id == R.id.bottom_about) {
                    loadFrag(new AboutFragment(), false);
                } else if (id == R.id.bottom_profile) {
                    loadFrag(new ProfileFragment(), false);
                }
                return true;
            }
        });

        bnView.setSelectedItemId(R.id.bottom_home);

    }
    public void loadFrag(Fragment fragment, boolean flag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (flag){
            ft.add(R.id.frameContainer, fragment);
        }else{
            ft.replace(R.id.frameContainer, fragment);
        }
        ft.commit();
    }
}