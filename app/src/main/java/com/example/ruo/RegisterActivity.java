package com.example.ruo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView textForgotPass = findViewById(R.id.textForgotPass);
        SpannableString spannableString = new SpannableString(getString(R.string.forgotPassword));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                // Menghilangkan garis bawah dari teks tautan
                ds.setUnderlineText(false);
            }
        };

        // supaya bisa ClickableSpan pada teks
        spannableString.setSpan(clickableSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        // Atur teks dalam TextView
        textForgotPass.setText(spannableString);

        // Aktifin tautan untuk bisa diklik
        textForgotPass.setMovementMethod(LinkMovementMethod.getInstance());

        ImageView imgBack = findViewById(R.id.btn_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
            }
        });

        Button btnRegister = findViewById(R.id.buttonRegister);
        TextInputLayout usernameLayout = findViewById(R.id.usernameRegister);
        TextInputEditText usernameInput = (TextInputEditText) usernameLayout.getEditText();

        TextInputLayout emailLayout = findViewById(R.id.emailRegister);
        TextInputEditText emailInput = (TextInputEditText) emailLayout.getEditText();

        TextInputLayout passLayout = findViewById(R.id.passwordRegister);
        TextInputEditText passInput = (TextInputEditText) passLayout.getEditText();


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameInput.getText().toString().trim();
                String email = emailInput.getText().toString().trim();
                String password = passInput.getText().toString().trim();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Silahkan Lengkapi Data Registrasi Akun", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Registrasi Akun Berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }


            }
        });
    }
}