package com.example.ruo.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ruo.API.APIProfile;
import com.example.ruo.API.APITherapy;
import com.example.ruo.APIClient;
import com.example.ruo.Home.HomeActivity1;
import com.example.ruo.LoginActivity;
import com.example.ruo.R;
import com.example.ruo.Therapy.MyAdapterTherapy;
import com.example.ruo.Therapy.TherapyActivity1;
import com.example.ruo.Therapy.TherapyActivityAdd;
import com.example.ruo.pojo.Profile.DataMyMessageItem;
import com.example.ruo.pojo.Profile.DataUserResponse;
import com.example.ruo.pojo.Profile.MyMessageResponse;
import com.example.ruo.pojo.Therapy.AllTherapyItem;
import com.example.ruo.pojo.Therapy.AllTherapyResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UtasAdapter utasAdapter;
//    private ArrayList<UtasModel> utasModel;
    APIProfile apiProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        MenuItem profileItem = bottomNavigationView.getMenu().findItem(R.id.item_profile);
        profileItem.setChecked(true);

        profileItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                return false;
            }
        });

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

        ImageView btnToAddUtas = findViewById(R.id.btnToAddUtas);
        btnToAddUtas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddUtasActivity.class);
                startActivity(intent);
            }
        });

//        getData();

        recyclerView = findViewById(R.id.recycleutas);
//        utasAdapter = new UtasAdapter(utasModel);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProfileActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(utasAdapter);

        apiProfile = APIClient.getClient().create(APIProfile.class);
        SharedPreferences sharedPref = getSharedPreferences("env", Context.MODE_PRIVATE);
        String authToken = "Bearer " + sharedPref.getString("ACCESS_TOKEN_SECRET", null);
        Integer id_user = sharedPref.getInt("id_user", 0);

        if (authToken != null) {
            Call<DataUserResponse> call = apiProfile.getDataUSerResp(authToken, id_user);
            call.enqueue(new Callback<DataUserResponse>() {
                @Override
                public void onResponse(Call<DataUserResponse> call, Response<DataUserResponse> response) {
                    if (response.isSuccessful()) {
                        TextView namauser = findViewById(R.id.textView22);
                        namauser.setText(response.body().getData().getUsername());
                        TextView gmailuser = findViewById(R.id.textView23);
                        gmailuser.setText(response.body().getData().getEmail());
                        TextView sosmeduser = findViewById(R.id.textView24);
                        sosmeduser.setText(response.body().getData().getMedsos());
                        TextView deskuser = findViewById(R.id.textView25);
                        deskuser.setText(response.body().getData().getDescription());
                        ImageView photouser = findViewById(R.id.imageView10);

                        //buat variabel baru ya amal
                        String photousernih = response.body().getData().getFotoUser();

                        if (photousernih.startsWith("http")) {
                            Picasso.get().load(photousernih).into(photouser);
                        } else {
                            // Jika fotoPsikolog adalah nama file,  URL dari server
                            String baseUrl = "http://10.0.2.2:3000";
                            String photoUrl = baseUrl + "/fotoProfile/" + photousernih;
                            Log.d("TAG url foto", "onBindViewHolder: " + photoUrl);
                            Picasso.get().load(photoUrl).fit().into(photouser);
                        }

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

                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(ProfileActivity.this);
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
                public void onFailure(Call<DataUserResponse> call, Throwable t) {
                }
            });
        } else {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        ///INI UNTUK YANG UTAS
        if (authToken != null) {
            Call<MyMessageResponse> call = apiProfile.getMyMessageResp(authToken, id_user);
            call.enqueue(new Callback<MyMessageResponse>() {
                @Override
                public void onResponse(Call<MyMessageResponse> call, Response<MyMessageResponse> response) {
                    if (response.isSuccessful()) {
                        List<DataMyMessageItem> allMessageItems = response.body().getDataMyMessage();
                        if (allMessageItems.size() > 0 && allMessageItems != null) {
                            ArrayList<DataMyMessageItem> MessageDataList = new ArrayList<>();

                            for (int i = 0; i < response.body().getDataMyMessage().size(); i++) {
                                DataMyMessageItem MessageData = new DataMyMessageItem(
                                        allMessageItems.get(i).getIsiMessage(),
                                        allMessageItems.get(i).getIdMessage()
                                );
                                MessageDataList.add(MessageData);
                            }
                            UtasAdapter adapterUtas = new UtasAdapter(getApplicationContext(), MessageDataList);
                            recyclerView.setAdapter(adapterUtas);
                        } else {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }
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

                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(ProfileActivity.this);
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
                public void onFailure(Call<MyMessageResponse> call, Throwable t) {
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(ProfileActivity.this);
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


//
//                                // Tambah ke ArrayList
//                                MessageDataList.add(MessageData );
//                            // Buat adapter
//                            UtasAdapter adapterUtas= new UtasAdapter(getApplicationContext(), MessageDataList);
//                            // Set adapter ke RecyclerView
//                            recyclerView.setAdapter(adapterUtas);

//                        } else {
//                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                            startActivity(intent);
//                        }
//                    } else {
//                        // JSON dari respons body
//                        JSONObject errorBody = null;
//                        try {
//                            errorBody = new JSONObject(response.errorBody().string());
//                            Log.d("TAG","" + errorBody);
//                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                        String errorMessage = null;
//                        try {
//                            errorMessage = errorBody.getString("message");
//                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
//                        }
//
//                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(TherapyActivity1.this);
//                        builder
//                                .setMessage(errorMessage)
//                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        // Tindakan yang diambil ketika tombol "OK" diklik
//                                        dialog.dismiss();
//                                    }
//                                })
//                                .show();
//
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<AllTherapyResponse> call, Throwable t) {
//                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(TherapyActivity1.this);
//                    builder
//                            .setMessage(t.getMessage())
//                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // Tindakan yang diambil ketika tombol "OK" diklik
//                                    dialog.dismiss();
//                                }
//                            })
//                            .show();
//                }
//            });
//        } else {
//            Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
//            startActivity(intent1);
//        }



    }

//    private void getData() {
//        utasModel = new ArrayList<>();
//        utasModel.add(new UtasModel("duh, cape banget deh. kenapa sih skripsi ini rumit banget"));
//        utasModel.add(new UtasModel("Skripsi ini adalah perjalanan yang tak pernah kukira sebelumnya. Terkadang, aku merasa seperti sedang berlari tanpa arah, mencoba memahami setiap huruf yang kutulis, dan sering kali merasa terombang-ambing di lautan informasi. Hari demi hari berlalu, dan skripsi ini masih menjadi 'kisah panjang' dalam hidupku. Aku begitu berharap bisa menyelesaikannya dengan segera, tapi realitasnya tidak pernah sesederhana itu."));
//        utasModel.add(new UtasModel("Ketemu teman: 'Udah selesai skripsinya?' Aku: 'Belum juga, susah banget."));
//        utasModel.add(new UtasModel("Tiap hari berkutat sama skripsi, akhirnya lupa sama dunia luar."));
//        utasModel.add(new UtasModel("Skripsi ini telah mengambil begitu banyak energi dan waktu dalam hidupku. Aku tak pernah menduga bahwa sebuah tugas akademik bisa menjadi begitu melelahkan. Sering kali aku harus menjalani malam tanpa tidur, mencari referensi yang tak kunjung selesai, dan merasa takut akan deadline yang semakin mendekat. "));
//        utasModel.add(new UtasModel("Jadi pingin nangis, skripsi ini bikin stres terus."));
//        utasModel.add(new UtasModel("Setiap kali aku mencoba menulis, kata-kata tampak begitu sulit ditemukan. Dan terkadang, aku merasa seperti semuanya tak akan pernah selesai. Aku harus terus berjuang, berusaha keras, dan percaya bahwa pada akhirnya aku akan melihat sinar terang di ujung terowongan"));
//    }

}