package com.example.ruo.Profile;

import androidx.annotation.NonNull;
import  androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ruo.API.APIProfile;
import com.example.ruo.API.APITherapy;
import com.example.ruo.APIClient;
import com.example.ruo.Home.HomeActivity1;
import com.example.ruo.Home.HomeActivity2;
import com.example.ruo.Home.HomeActivity3;
import com.example.ruo.LoginActivity;
import com.example.ruo.R;
import com.example.ruo.Therapy.TherapyActivity1;
import com.example.ruo.Therapy.TherapyActivity2;
import com.example.ruo.Therapy.TherapyActivityEdit;
import com.example.ruo.message.MessageActivity;
import com.example.ruo.pojo.Profile.DetailMyMessageResponse;
import com.example.ruo.pojo.Profile.DetailMyMessage;
import com.example.ruo.pojo.Profile.MyMessageResponse;
import com.example.ruo.pojo.Profile.EditMyMessageResponse;
import com.example.ruo.pojo.Therapy.DetailTherapyResponse;
import com.example.ruo.pojo.Therapy.EditTherapyResponse;
import com.example.ruo.pojo.chatTerry.Answer1Response;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUtasActivity extends AppCompatActivity {
    APIProfile apiProfile;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_utas);

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


        ImageView btnBackEditUtas= findViewById(R.id.btnBackEditUtas);
        btnBackEditUtas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        TextInputLayout utasLayout = findViewById(R.id.utasinput);
        TextInputEditText utasInput = (TextInputEditText) utasLayout.getEditText();

        Intent intent = getIntent();
        if (intent != null) {
            int utasId = intent.getIntExtra("utasId", 0);

        apiProfile = APIClient.getClient().create(APIProfile.class);
        SharedPreferences sharedPref = getSharedPreferences("env", Context.MODE_PRIVATE);
        String authToken = "Bearer " + sharedPref.getString("ACCESS_TOKEN_SECRET", null);

            if (authToken != null){
                Call<DetailMyMessageResponse> call = apiProfile.getDetailMessageKu(authToken, utasId);
                call.enqueue(new Callback<DetailMyMessageResponse>() {
                    @Override
                    public void onResponse(Call<DetailMyMessageResponse> call, Response<DetailMyMessageResponse> response) {
                        if (response.isSuccessful()){
                            utasInput.setText(response.body().getDetailMyMessage().getIsiMessage());
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

                            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(EditUtasActivity.this);
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
                    public void onFailure(Call<DetailMyMessageResponse> call, Throwable t) {
                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(EditUtasActivity.this);
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
                Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent1);
            }
        } else {
            Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent1);
        }

        Button btnEditUtas = findViewById(R.id.buttonEditUtas);
        btnEditUtas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String utas = utasInput.getText().toString().trim();

                Intent intent = getIntent();
                if (intent != null) {
                    int utasId = intent.getIntExtra("utasId", 0);
                    apiProfile = APIClient.getClient().create(APIProfile.class);
                    SharedPreferences sharedPref = getSharedPreferences("env", Context.MODE_PRIVATE);
                    String authToken = "Bearer " + sharedPref.getString("ACCESS_TOKEN_SECRET", null);

                    if (authToken != null) {
                        Call<EditMyMessageResponse> call = apiProfile.postMyMessageResp(authToken, utasId, utas);
                        call.enqueue(new Callback<EditMyMessageResponse>() {
                            @Override
                            public void onResponse(Call<EditMyMessageResponse> call, Response<EditMyMessageResponse> response) {
                                if (response.isSuccessful()) {
//                                    utasInput.setText(response.body().getMessage());
                                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(EditUtasActivity.this);
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
                                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(EditUtasActivity.this);
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
                            public void onFailure(Call<EditMyMessageResponse> call, Throwable t) {
                                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(EditUtasActivity.this);
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
                        Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent1);
                    }
                }
            }
    });
}
}
