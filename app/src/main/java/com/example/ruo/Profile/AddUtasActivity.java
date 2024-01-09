package com.example.ruo.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ruo.API.APIProfile;
import com.example.ruo.APIClient;
import com.example.ruo.Home.HomeActivity1;
import com.example.ruo.LoginActivity;
import com.example.ruo.R;
import com.example.ruo.Therapy.TherapyActivity1;
import com.example.ruo.Therapy.TherapyActivity2;
import com.example.ruo.Therapy.TherapyActivityAdd;
import com.example.ruo.message.MessageActivity;
import com.example.ruo.pojo.Profile.AddMyMessageResponse;
import com.example.ruo.pojo.Therapy.AddTherapyResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUtasActivity extends AppCompatActivity {

    APIProfile apiProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_utas);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        MenuItem therapyItem = bottomNavigationView.getMenu().findItem(R.id.item_therapy);


        therapyItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent intent = new Intent(getApplicationContext(), TherapyActivity1.class);
                startActivity(intent);
                return false;
            }
        });

        MenuItem homeItem = bottomNavigationView.getMenu().findItem(R.id.item_home);
        homeItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity1.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                return false;
            }
        });

        MenuItem messageItem = bottomNavigationView.getMenu().findItem(R.id.item_message);
        messageItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                return true;
            }
        });

        MenuItem profileItem = bottomNavigationView.getMenu().findItem(R.id.item_profile);
        profileItem.setChecked(true);
        profileItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                return true;
            }
        });

        ImageView btnBackAddUtas= findViewById(R.id.btnBackAddUtas);
        btnBackAddUtas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        TextInputLayout tambahutas = findViewById(R.id.addutasinput);
        TextInputEditText addUtasinput = (TextInputEditText) tambahutas.getEditText();

        Button btnAddUtas = findViewById(R.id.buttonSendUtas);
        btnAddUtas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addUtas = addUtasinput.getText().toString().trim();

                if (addUtas.isEmpty()){
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(AddUtasActivity.this);
                    materialAlertDialogBuilder
                            .setMessage("Please Input Utas First")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                } else{
                    apiProfile = APIClient.getClient().create(APIProfile.class);
                    SharedPreferences sharedPref = getSharedPreferences("env", Context.MODE_PRIVATE);
                    String authToken = "Bearer " + sharedPref.getString("ACCESS_TOKEN_SECRET", null);
                    Integer id_user = sharedPref.getInt("id_user", 0);

                    //    utasInput.setText(response.body().getDetailMyMessage().getIsiMessage());

                    if(authToken != null) {
                        Call<AddMyMessageResponse> call = apiProfile.postAddMyMessageResp(authToken, id_user, addUtas );
                        call.enqueue(new Callback<AddMyMessageResponse>() {
                            @Override
                            public void onResponse(Call<AddMyMessageResponse> call, Response<AddMyMessageResponse> response) {
                                if (response.isSuccessful()){
                                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(AddUtasActivity.this);
                                    materialAlertDialogBuilder
                                            .setMessage(response.body().getMessage())
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                                                    startActivity(intent);
                                                }
                                            }).show();
                                } else {
                                    // JSON dari respons body
                                    JSONObject errorBody = null;
                                    try {
                                        errorBody = new JSONObject(response.errorBody().string());
                                        Log.d("TAG", "" + errorBody);
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

                                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(AddUtasActivity.this);
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
                            public void onFailure(Call<AddMyMessageResponse> call, Throwable t) {
                                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(AddUtasActivity.this);
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
                    } else {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

    }
    public static class EditProfileActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_profile);
        }
    }
}