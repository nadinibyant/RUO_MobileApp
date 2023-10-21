package com.example.ruo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TherapyActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapy1);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        MenuItem homeItem = bottomNavigationView.getMenu().findItem(R.id.item_therapy);
        homeItem.setChecked(true);
    }
}