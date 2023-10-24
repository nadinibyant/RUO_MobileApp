package com.example.ruo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class HomeActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        MenuItem homeItem = bottomNavigationView.getMenu().findItem(R.id.item_home);
        homeItem.setChecked(true);

        TextInputLayout answer1Layout = findViewById(R.id.answer1);
        TextInputEditText answer1Input = (TextInputEditText) answer1Layout.getEditText();

        Button btnSendAnswer1 = findViewById(R.id.buttonSendAnswer1);
        btnSendAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer1 = answer1Input.getText().toString().trim();
                if (answer1.isEmpty()){
                    Toast.makeText(HomeActivity2.this, "Silahkan Jawab Pertanyaan Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity3.class);
                    intent.putExtra("answer1", answer1);
                    startActivity(intent);
                }
            }
        });
    }
}