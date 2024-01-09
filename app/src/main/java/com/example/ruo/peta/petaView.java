package com.example.ruo.peta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.ruo.API.APITherapy;
import com.example.ruo.APIClient;
import com.example.ruo.LoginActivity;
import com.example.ruo.R;
import com.example.ruo.pojo.peta.PetaResponse;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.encoders.json.BuildConfig;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.views.MapView;
import org.osmdroid.util.GeoPoint;


import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.views.overlay.Marker;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class petaView extends AppCompatActivity {
    APITherapy apiTherapy;

    private  MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peta_view);

        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        Intent intent = getIntent();
        int therapyId = intent.getIntExtra("therapyId", 0);

        apiTherapy = APIClient.getClient().create(APITherapy.class);
        SharedPreferences sharedPref = getSharedPreferences("env", Context.MODE_PRIVATE);
        String authToken = "Bearer " + sharedPref.getString("ACCESS_TOKEN_SECRET", null);

        if (authToken != null) {
            Call<PetaResponse> call = apiTherapy.getPetaResp(authToken, therapyId);
            call.enqueue(new Callback<PetaResponse>() {
                @Override
                public void onResponse(Call<PetaResponse> call, Response<PetaResponse> response) {
                    if (response.isSuccessful()){
                        mapView = findViewById(R.id.mapView);

                        double latitude = (double) response.body().getLocation().getLatitude();
                        double longitude = (double) response.body().getLocation().getLongitude();

                        Log.d("tag latitude", "onResponse: " + latitude);
                        Log.d("tag longtitude", "onResponse: " + longitude);
                        mapView.setTileSource(TileSourceFactory.MAPNIK);

                        GeoPoint location = new GeoPoint(latitude, longitude);
                        Marker marker = new Marker(mapView);
                        marker.setPosition(location);
                        mapView.getOverlays().add(marker);

                        BoundingBox boundingBoxIndonesia = new BoundingBox(
                                -11.0, // South latitude (bottom of Indonesia)
                                95.0,  // West longitude (left side of Indonesia)
                                6.0,   // North latitude (top of Indonesia)
                                141.0  // East longitude (right side of Indonesia)
                        );

// Set an appropriate zoom level
                        double maxZoomLevel = 6.0; // Adjust this value based on your preference
                        mapView.zoomToBoundingBox(boundingBoxIndonesia, true, (int) maxZoomLevel);
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

                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(petaView.this);
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
                public void onFailure(Call<PetaResponse> call, Throwable t) {
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(petaView.this);
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
            Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intentLogin);
        }



    }
}