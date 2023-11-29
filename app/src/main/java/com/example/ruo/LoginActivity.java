package com.example.ruo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ruo.API.APIUser;
import com.example.ruo.ForgotPassword.ForgotPasswordActivity;
import com.example.ruo.Home.HomeActivity1;
import com.example.ruo.pojo.LoginResponse;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    APIUser apiuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        Button btnLogin = findViewById(R.id.buttonLogin);
        TextInputLayout usernameLayout = findViewById(R.id.usernamelogin);
        TextInputEditText usernameInput = (TextInputEditText) usernameLayout.getEditText();

        TextInputLayout passwordLayout = findViewById(R.id.passwordLogin);
        TextInputEditText passwordInput = (TextInputEditText) passwordLayout.getEditText();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                if (username.isEmpty()){
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(LoginActivity.this);
                    builder
                            .setMessage("Please Complete Your Username Data")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Tindakan yang diambil ketika tombol "OK" diklik
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else if (password.isEmpty()){
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(LoginActivity.this);
                    builder
                            .setMessage("Please Complete Your Password Data")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Tindakan yang diambil ketika tombol "OK" diklik
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else {
                    apiuser = APIClient.getClient().create(APIUser.class);
                    Call<LoginResponse> call = apiuser.getLoginResp(username = username, password = password);
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful() == true) {
                                LoginResponse loginResponse = response.body();

                                SharedPreferences sharedPref = getSharedPreferences("env", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("ACCESS_TOKEN_SECRET", loginResponse.getToken());
                                editor.putInt("id_user", loginResponse.getIdUser());
                                editor.apply();



                                String authToken = sharedPref.getString("ACCESS_TOKEN_SECRET", null);
                                Log.d("TAG", "token login " + authToken);

                                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(LoginActivity.this);
                                builder
                                        .setMessage(loginResponse.getMessage())
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(getApplicationContext(), HomeActivity1.class);
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

                                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(LoginActivity.this);
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
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(LoginActivity.this);
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