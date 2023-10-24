package com.example.ruo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        MenuItem homeItem = bottomNavigationView.getMenu().findItem(R.id.item_home);
        homeItem.setChecked(true);

        homeItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent inten = new Intent(getApplicationContext(), HomeActivity1.class);
                startActivity(inten);
                return true;
            }
        });

        MenuItem therapyItem = bottomNavigationView.getMenu().findItem(R.id.item_therapy);
        therapyItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent intent = new Intent(getApplicationContext(), TherapyActivity1.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                return true;
            }
        });

//        MenuItem messageItem = bottomNavigationView.getMenu().findItem(R.id.item_message);
//        messageItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
//                Intent intent = new Intent(getApplicationContext(), )
//                return false;
//            }
//        })

        ImageView btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity2.class);
                startActivity(intent);
            }
        });




    }


}