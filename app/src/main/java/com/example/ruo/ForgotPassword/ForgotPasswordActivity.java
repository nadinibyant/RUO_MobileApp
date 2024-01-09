package com.example.ruo.ForgotPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ruo.API.APIUser;
import com.example.ruo.APIClient;
import com.example.ruo.Home.HomeActivity2;
import com.example.ruo.LoginActivity;
import com.example.ruo.R;
import com.example.ruo.pojo.ForgetPassResponse;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    APIUser apiUser;
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
                    apiUser = APIClient.getClient().create(APIUser.class);
                    Call<ForgetPassResponse> call = apiUser.getForgetPassResp(email, newPass, confirm);
                    call.enqueue(new Callback<ForgetPassResponse>() {
                        @Override
                        public void onResponse(Call<ForgetPassResponse> call, Response<ForgetPassResponse> response) {
                            if (response.isSuccessful()){
                                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(ForgotPasswordActivity.this);
                                materialAlertDialogBuilder
                                        .setMessage(response.body().getMessage())
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                            startActivity(intent);
                                        }
                                }).show();
                            } else {
                                // JSON dari respons body
                                JSONObject errorBody = null;
                                try {
                                    errorBody = new JSONObject(response.errorBody().string());
                                    Log.d("TAG","" + errorBody);
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

                                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(ForgotPasswordActivity.this);
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
                        public void onFailure(Call<ForgetPassResponse> call, Throwable t) {

                        }
                    });
//                    if (newPass.equalsIgnoreCase(confirm)){
//
//
//                    } else {
//                        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(ForgotPasswordActivity.this);
//                        materialAlertDialogBuilder
//                                .setMessage("Your New Password Don't Match with Confirm New Password")
//                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        dialogInterface.dismiss();
//                                    }
//                                }).show();
//                    }
                }
            }
        });


    }
}