package com.example.ruo.ForgotPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ruo.LoginActivity;
import com.example.ruo.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        TextInputLayout emailLayout = findViewById(R.id.emailForget);
        TextInputEditText emailInput = (TextInputEditText) emailLayout.getEditText();

        TextInputLayout newPassLayout = findViewById(R.id.newPasswordForget);
        TextInputEditText newPassInput = (TextInputEditText) newPassLayout.getEditText();

        TextInputLayout confirmNewPassLayout = findViewById(R.id.ConfirmNewPasswordForget);
        TextInputEditText confirmNewPass = (TextInputEditText) confirmNewPassLayout.getEditText();

        Button btnForgot = findViewById(R.id.buttonForget);
        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString().trim();
                String newPass = newPassInput.getText().toString().trim();
                String confirm = confirmNewPass.getText().toString().trim();

                if (email.isEmpty()){
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(ForgotPasswordActivity.this);
                    materialAlertDialogBuilder
                            .setMessage("Please Complete Your Email Data")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();
                } else if (newPass.isEmpty()){
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(ForgotPasswordActivity.this);
                    materialAlertDialogBuilder
                            .setMessage("Please Complete Your New Password Data")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                } else if (confirm.isEmpty()){
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(ForgotPasswordActivity.this);
                    materialAlertDialogBuilder
                            .setMessage("Please Complete Your Confirm New Password Data")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                } else {
                    if (newPass.equalsIgnoreCase(confirm)){
                        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(ForgotPasswordActivity.this);
                        materialAlertDialogBuilder
                                .setMessage("Your Password Is Change")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                    }
                                }).show();

                    } else {
                        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(ForgotPasswordActivity.this);
                        materialAlertDialogBuilder
                                .setMessage("Your New Password Don't Match with Confirm New Password")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                    }
                }
            }
        });


    }
}