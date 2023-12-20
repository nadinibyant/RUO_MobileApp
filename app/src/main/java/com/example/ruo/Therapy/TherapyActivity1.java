package com.example.ruo.Therapy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ruo.API.APITherapy;
import com.example.ruo.APIClient;
import com.example.ruo.Home.HomeActivity1;
import com.example.ruo.LoginActivity;
import com.example.ruo.R;
import com.example.ruo.pojo.Therapy.AllTherapyItem;
import com.example.ruo.pojo.Therapy.AllTherapyResponse;
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

public class TherapyActivity1 extends AppCompatActivity {

    APITherapy apiTherapy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapy1);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        MenuItem therapyItem = bottomNavigationView.getMenu().findItem(R.id.item_therapy);
        therapyItem.setChecked(true);

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

        TextView textMyTherapy = findViewById(R.id.textMyTherapy);
        SpannableString spannableString = new SpannableString(getString(R.string.My_Therapy));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(getApplicationContext(), TherapyActivity2.class);
                startActivity(intent);
            }

        };

        // supaya bisa ClickableSpan pada teks
        spannableString.setSpan(clickableSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        // Atur teks dalam TextView
        textMyTherapy.setText(spannableString);

        // Aktifin tautan untuk bisa diklik
        textMyTherapy.setMovementMethod(LinkMovementMethod.getInstance());

        RecyclerView recyclerView = findViewById(R.id.rv_listTherapy);
        final MyAdapterTherapy[] adapterTherapy = new MyAdapterTherapy[1];
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiTherapy = APIClient.getClient().create(APITherapy.class);
        SharedPreferences sharedPref = getSharedPreferences("env", Context.MODE_PRIVATE);
        String authToken = "Bearer " + sharedPref.getString("ACCESS_TOKEN_SECRET", null);

        if (authToken != null) {

            Call<AllTherapyResponse> call = apiTherapy.getAllTherapyResp(authToken);
            call.enqueue(new Callback<AllTherapyResponse>() {
                @Override
                public void onResponse(Call<AllTherapyResponse> call, Response<AllTherapyResponse> response) {
                    if (response.isSuccessful()){
                        List<AllTherapyItem> allTherapyItems = response.body().getAllTherapy();
                        if (allTherapyItems.size() > 0 && allTherapyItems != null){

                            ArrayList<AllTherapyItem> therapyDataList = new ArrayList<>();

                            for (int i = 0; i < response.body().getAllTherapy().size(); i++) {
                                AllTherapyItem therapyData = new AllTherapyItem (
                                        allTherapyItems.get(i).getIdTherapy(),
                                        allTherapyItems.get(i).getFotoPsikolog(),
                                        allTherapyItems.get(i).getLamaKarir(),
                                        allTherapyItems.get(i).getNamaPsikolog(),
                                        allTherapyItems.get(i).getSpesialisPsikolog(),
                                        allTherapyItems.get(i).getNoTelpPsikolog(),
                                        allTherapyItems.get(i).getMedsosPsikolog(),
                                        R.drawable.like,
                                        R.drawable.unlike,
                                        allTherapyItems.get(i).getLike(),
                                        allTherapyItems.get(i).getDislike()
                                );

                                // Tambah ke ArrayList
                                therapyDataList.add(therapyData);

                            }

                            // Buat adapter
                            MyAdapterTherapy adapterTherapy = new MyAdapterTherapy(getApplicationContext(), therapyDataList);

                            // Set adapter ke RecyclerView
                            recyclerView.setAdapter(adapterTherapy);
                        } else {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }
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

                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(TherapyActivity1.this);
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
                public void onFailure(Call<AllTherapyResponse> call, Throwable t) {
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(TherapyActivity1.this);
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