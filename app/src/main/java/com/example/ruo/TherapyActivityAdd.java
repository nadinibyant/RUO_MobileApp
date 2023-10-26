package com.example.ruo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class TherapyActivityAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapy_add);

        ImageView btnBackAddTherapy = findViewById(R.id.btnBackTherapyAdd);
        btnBackAddTherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TherapyActivity2.class);
                startActivity(intent);
            }
        });

        TextInputLayout nameLayout = findViewById(R.id.inputNamaTherapy);
        TextInputEditText nameInput = (TextInputEditText) nameLayout.getEditText();

        TextInputLayout spesialistLayout = findViewById(R.id.inputSpesialistTherapy);
        TextInputEditText spesialistInput = (TextInputEditText) spesialistLayout.getEditText();

        TextInputLayout lamaKerjaLayout = findViewById(R.id.inputLamaKerjaTherapy);
        TextInputEditText lamaKerjaInput = (TextInputEditText) lamaKerjaLayout.getEditText();

        TextInputLayout noTelpLayout = findViewById(R.id.inputNoTelpTherapy);
        TextInputEditText noTelpInput = (TextInputEditText) noTelpLayout.getEditText();

        TextInputLayout instagramLayout = findViewById(R.id.inputInstagramTherapy);
        TextInputEditText instagramInput = (TextInputEditText) instagramLayout.getEditText();

        Button btnAddTherapy = findViewById(R.id.btnAddDataTherapy);
        btnAddTherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameInput.getText().toString().trim();
                String spesialist = spesialistInput.getText().toString().trim();
                String lamaKerja = lamaKerjaInput.getText().toString().trim();
                String noTelp = noTelpInput.getText().toString().trim();
                String instagram = instagramInput.getText().toString().trim();

                if (name.isEmpty()){
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(TherapyActivityAdd.this);
                    materialAlertDialogBuilder
                            .setMessage("Please Complete Your Name Data")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                } else if (spesialist.isEmpty()){
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(TherapyActivityAdd.this);
                    materialAlertDialogBuilder
                            .setMessage("Please Complete Your Spesialist Data")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                } else if (lamaKerja.isEmpty()){
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(TherapyActivityAdd.this);
                    materialAlertDialogBuilder
                            .setMessage("Please Complete Your Long Career Data")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                } else if (noTelp.isEmpty()){
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(TherapyActivityAdd.this);
                    materialAlertDialogBuilder
                            .setMessage("Please Complete Your No Phone Data")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                } else if (instagram.isEmpty()){
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(TherapyActivityAdd.this);
                    materialAlertDialogBuilder
                            .setMessage("Please Complete Your Instagram Data")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                } else {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(TherapyActivityAdd.this);
                    materialAlertDialogBuilder
                            .setMessage("Data Added Successfully")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(getApplicationContext(), TherapyActivity2.class);
                                    startActivity(intent);
                                }
                            }).show();
                }
            }
        });
    }
}