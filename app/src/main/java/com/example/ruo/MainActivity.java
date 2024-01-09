package com.example.ruo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Membuat Handler untuk menangani jeda waktu 3 detik
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Intent untuk membuka halaman tujuan
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
                finish(); // Optional: Menutup halaman saat kembali
            }
        }, 3000); // Jeda waktu dalam milidetik (3 detik)
    }

}