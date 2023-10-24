package com.example.ruo;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class HomeActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home3);

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

        Intent intent = getIntent();
        String answer1 = intent.getStringExtra("answer1");
        TextView answer1Card = findViewById(R.id.textView7);
        answer1Card.setText(answer1);

        TextInputLayout answer2Layout = findViewById(R.id.answer2);
        TextInputEditText answer2Input = (TextInputEditText) answer2Layout.getEditText();

        Button buttonAnswer2 = findViewById(R.id.buttonSendAnswer2);
        buttonAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer2 = answer2Input.getText().toString().trim();

                if (answer2.isEmpty()) {
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(HomeActivity3.this);
                    builder
                            .setMessage("Silahkan Lengkapi Jawaban terlebih Dahulu")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Tindakan yang diambil ketika tombol "OK" diklik
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else {
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(HomeActivity3.this);
                    builder
                            .setMessage("Terimakasih Sudah Menjawab Pertanyaan RUO")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getApplicationContext(), TherapyActivity1.class);
                                    startActivity(intent);
                                }
                            })
                            .show();
                }
            }
        });
    }
}