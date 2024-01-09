package com.example.ruo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

import com.example.ruo.API.APIUser;
import com.example.ruo.ForgotPassword.ForgotPasswordActivity;
import com.example.ruo.pojo.RegisterResponse;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    APIUser apiuser;

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
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(RegisterActivity.this);
                    builder
                            .setMessage("Please Complete The Account Registration Data")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else {
                    apiuser = APIClient.getClient().create(APIUser.class);
                    Call<RegisterResponse> call = apiuser.getRegistResp(username = username, email = email, password = password);
                    call.enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                            if (response.isSuccessful()){
                                RegisterResponse registerResponse = response.body();
                                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(RegisterActivity.this);
                                builder
                                        .setMessage(registerResponse.getMessage())
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .show();
                            } else {
                                // mengurai pesan JSON dari respons body
                                JSONObject errorBody = null;
                                try {
                                    errorBody = new JSONObject(response.errorBody().string());
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                String errorMessage = null;
                                try {
                                    errorMessage = errorBody.getString("message");
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(RegisterActivity.this);
                                builder
                                        .setMessage(errorMessage)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // Tindakan yang diambil ketika tombol "OK" diklik
                                                dialog.dismiss();
                                            }
                                        })
                                        .show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {
                            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(RegisterActivity.this);
                            builder
                                    .setMessage(t.getMessage())
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Tindakan yang diambil ketika tombol "OK" diklik
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();
                        }
                    });
                }
            }
        });
    }
}