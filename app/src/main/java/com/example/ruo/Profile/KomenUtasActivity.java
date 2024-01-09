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
import com.example.ruo.APIClient;
import com.example.ruo.Home.HomeActivity1;
import com.example.ruo.KomenUtasAdapter;
import com.example.ruo.KomenUtasModel;
import com.example.ruo.LoginActivity;
import com.example.ruo.R;
import com.example.ruo.Therapy.TherapyActivity1;
import com.example.ruo.message.MessageActivity;
import com.example.ruo.pojo.Profile.CommentMessageResponse;
import com.example.ruo.pojo.Profile.DataCommentItem;
import com.example.ruo.pojo.Profile.DataMyMessageItem;
import com.example.ruo.pojo.Profile.MyMessageResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KomenUtasActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private KomenUtasAdapter komenUtasAdapter;
//    private ArrayList<KomenUtasModel> komenUtasModel;
    APIProfile apiProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komen_utas);

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

        MenuItem messageItem = bottomNavigationView.getMenu().findItem(R.id.item_message);
        messageItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                return false;
            }
        });

        ImageView btnBackEditUtas= findViewById(R.id.btnBackKomenUtas);
        btnBackEditUtas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();

        TextView tvChosenUtas = findViewById(R.id.tvChosenUtas);
        if (intent != null) {
            String chosenUtasMessage = intent.getStringExtra("utasMessage");
            if (chosenUtasMessage != null) {
                tvChosenUtas.setText(chosenUtasMessage);
            }
        }

            apiProfile = APIClient.getClient().create(APIProfile.class);
            SharedPreferences sharedPref = getSharedPreferences("env", Context.MODE_PRIVATE);
            String authToken = "Bearer " + sharedPref.getString("ACCESS_TOKEN_SECRET", null);
//            int utasId = intent.getIntExtra("utasId", 0);
//        Integer id_message = sharedPref.getInt("id_message", 0);

        //KALO GADA INI GAMUNCUL
        recyclerView = findViewById(R.id.recyclekomenutas);
//        utasAdapter = new UtasAdapter(utasModel);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(KomenUtasActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(komenUtasAdapter);

        int utasId = intent.getIntExtra("utasId", 0);

                ///INI UNTUK YANG UTAS
                if (authToken != null) {
                    Call<CommentMessageResponse> call = apiProfile.getCommentMessageResp(authToken,utasId);
                    call.enqueue(new Callback<CommentMessageResponse>() {
                        @Override
                        public void onResponse(Call<CommentMessageResponse> call, Response<CommentMessageResponse> response) {
                            if (response.isSuccessful()) {
                                List<DataCommentItem> allCommentMessageItems = response.body().getDataComment();

                                if (allCommentMessageItems.size() > 0 && allCommentMessageItems != null) {
                                    ArrayList<DataCommentItem> MessageCommentDataList = new ArrayList<>();

                                    for (int i = 0; i < response.body().getDataComment().size(); i++) {
                                        DataCommentItem MessageCommentData = new DataCommentItem(
                                                allCommentMessageItems.get(i).getIsiComment(),
                                                allCommentMessageItems.get(i).getIdComment()
                                        );
                                        MessageCommentDataList.add(MessageCommentData);
                                    }
                                    KomenUtasAdapter adapterKomenUtas = new KomenUtasAdapter(getApplicationContext(), MessageCommentDataList);
                                    recyclerView.setAdapter(adapterKomenUtas);
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

                                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(KomenUtasActivity.this);
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
                        public void onFailure(Call<CommentMessageResponse> call, Throwable t) {
                            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(KomenUtasActivity.this);
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



//                @Override
//                public void onResponse(Call<MyMessageResponse> call, Response<MyMessageResponse> response) {

//                @Override
//                public void onFailure(Call<MyMessageResponse> call, Throwable t) {
//                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(ProfileActivity.this);
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


//
//            getData();
//
//
//
//        recyclerView = findViewById(R.id.recyclekomenutas);
//        komenUtasAdapter = new KomenUtasAdapter(komenUtasModel);
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(KomenUtasActivity.this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(komenUtasAdapter);
        }

//    private void getData() {
//        komenUtasModel = new ArrayList<>();
//        komenUtasModel.add(new KomenUtasModel("Ini aku komen yang pertama yaaah"));
//        komenUtasModel.add(new KomenUtasModel("Ini aku komen yang pertama yaaah"));
//        komenUtasModel.add(new KomenUtasModel("Ini aku komen yang pertama yaaah"));
//    }
    }