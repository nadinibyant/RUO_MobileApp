package com.example.ruo.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.ruo.LoginActivity;
import com.example.ruo.Profile.ProfileActivity;
import com.example.ruo.R;
import com.example.ruo.Therapy.TherapyActivity1;
import com.example.ruo.message.MessageActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getSharedPreferences("env", Context.MODE_PRIVATE);
        String authToken = "Bearer " + sharedPref.getString("ACCESS_TOKEN_SECRET", null);
        String Token = sharedPref.getString("ACCESS_TOKEN_SECRET", null);

        if (Token != null){

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

        MenuItem messageItem = bottomNavigationView.getMenu().findItem(R.id.item_message);
        messageItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                return true;
            }
        });

        MenuItem profileItem = bottomNavigationView.getMenu().findItem(R.id.item_profile);
            profileItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                    return true;
                }
            });

            ImageView btnNext = findViewById(R.id.btn_next);
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity2.class);
                    startActivity(intent);
                }
            });
        } else {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }





    }


}