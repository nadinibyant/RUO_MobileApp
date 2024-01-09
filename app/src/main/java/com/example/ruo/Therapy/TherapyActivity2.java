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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.ruo.API.APITherapy;
import com.example.ruo.APIClient;
import com.example.ruo.Home.HomeActivity1;
import com.example.ruo.LoginActivity;
import com.example.ruo.R;
import com.example.ruo.pojo.Therapy.AllTherapyItem;
import com.example.ruo.pojo.Therapy.AllTherapyResponse;
import com.example.ruo.pojo.Therapy.MyTherapyItem;
import com.example.ruo.pojo.Therapy.MyTherapyResponse;
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

public class TherapyActivity2 extends AppCompatActivity {
    APITherapy apiTherapy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapy2);

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

        RecyclerView recyclerView = findViewById(R.id.rv_listTherapyIndividu);
        MyAdapterIndividuTherapy myAdapterIndividuTherapy;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiTherapy = APIClient.getClient().create(APITherapy.class);
        SharedPreferences sharedPref = getSharedPreferences("env", Context.MODE_PRIVATE);
        String authToken = "Bearer " + sharedPref.getString("ACCESS_TOKEN_SECRET", null);

        if (authToken != null) {
            Integer id_user = sharedPref.getInt("id_user", 0);
            Call<MyTherapyResponse> call = apiTherapy.getAllMyTherapyResp(authToken, id_user);
            call.enqueue(new Callback<MyTherapyResponse>() {
                @Override
                public void onResponse(Call<MyTherapyResponse> call, Response<MyTherapyResponse> response) {
                    if (response.isSuccessful()){
                        List<MyTherapyItem> myTherapyItems = response.body().getMyTherapy();
                        if (myTherapyItems.size() > 0 && myTherapyItems != null){
                            ArrayList<MyTherapyItem> myTherapyItemArrayList = new ArrayList<>();

                            for (int i = 0; i < myTherapyItems.size(); i++) {
                                MyTherapyItem myTherapyItem = new MyTherapyItem(
                                        myTherapyItems.get(i).getIdTherapy(),
                                        R.drawable.baseline_mode_edit_24,
                                        R.drawable.baseline_delete_24,
                                        myTherapyItems.get(i).getFotoPsikolog(),
                                        myTherapyItems.get(i).getNamaPsikolog(),
                                        myTherapyItems.get(i).getSpesialisPsikolog(),
                                        myTherapyItems.get(i).getLamaKarir(),
                                        myTherapyItems.get(i).getNoTelpPsikolog(),
                                        myTherapyItems.get(i).getMedsosPsikolog(),
                                        R.drawable.like,
                                        R.drawable.unlike,
                                        myTherapyItems.get(i).getLike(),
                                        myTherapyItems.get(i).getDislike(),
                                        myTherapyItems.get(i).getAlamatLengkap(),
                                        R.drawable.baseline_location_on_24
                                );
                                // Tambah ke ArrayList
                                myTherapyItemArrayList.add(myTherapyItem);
                            }

                            // Buat adapter
                            MyAdapterIndividuTherapy adapterIndividuTherapy = new MyAdapterIndividuTherapy(getApplicationContext(), myTherapyItemArrayList);

                            // Set adapter ke RecyclerView
                            recyclerView.setAdapter(adapterIndividuTherapy);
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

                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(TherapyActivity2.this);
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
                public void onFailure(Call<MyTherapyResponse> call, Throwable t) {
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(TherapyActivity2.this);
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


        ImageView btnToAddTherapy = findViewById(R.id.btnToAddTherapy);
        btnToAddTherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TherapyActivityAdd.class);
                startActivity(intent);
            }
        });

        ImageView btnBackMyTherapy = findViewById(R.id.btnBackMyTherapy);
        btnBackMyTherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TherapyActivity1.class);
                startActivity(intent);
            }
        });




    }
}