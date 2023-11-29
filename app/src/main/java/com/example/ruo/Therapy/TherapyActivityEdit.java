package com.example.ruo.Therapy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ruo.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class TherapyActivityEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapy_edit);

        ImageView btnBackEditTherapy = findViewById(R.id.btnBackEditTherapy);
       btnBackEditTherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TherapyActivity2.class);
                startActivity(intent);
            }
        });

        TextInputLayout nameLayout = findViewById(R.id.editNamaTherapy);
        TextInputEditText nameInput = (TextInputEditText) nameLayout.getEditText();

        TextInputLayout spesialistLayout = findViewById(R.id.editSpesialistTherapy);
        TextInputEditText spesialistInput = (TextInputEditText) spesialistLayout.getEditText();

        TextInputLayout lamaKerjaLayout = findViewById(R.id.editLamaKerjaTherapy);
        TextInputEditText lamaKerjaInput = (TextInputEditText) lamaKerjaLayout.getEditText();

        TextInputLayout noTelpLayout = findViewById(R.id.editNoTelpTherapy);
        TextInputEditText noTelpInput = (TextInputEditText) noTelpLayout.getEditText();

        TextInputLayout instagramLayout = findViewById(R.id.editInstagramTherapy);
        TextInputEditText instagramInput = (TextInputEditText) instagramLayout.getEditText();

        Button btnAddTherapy = findViewById(R.id.btnEditDataTherapy);
        btnAddTherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameInput.getText().toString().trim();
                String spesialist = spesialistInput.getText().toString().trim();
                String lamaKerja = lamaKerjaInput.getText().toString().trim();
                String noTelp = noTelpInput.getText().toString().trim();
                String instagram = instagramInput.getText().toString().trim();

                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(TherapyActivityEdit.this);
                materialAlertDialogBuilder
                        .setMessage("Data Updated Successfully")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), TherapyActivity2.class);
                                startActivity(intent);
                            }
                        }).show();
            }
        });

    }
}