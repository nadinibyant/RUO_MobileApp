package com.example.ruo.Therapy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ruo.API.APITherapy;
import com.example.ruo.APIClient;
import com.example.ruo.Home.HomeActivity3;
import com.example.ruo.LoginActivity;
import com.example.ruo.R;
import com.example.ruo.pojo.Therapy.DataTherapy;
import com.example.ruo.pojo.Therapy.DetailTherapyResponse;
import com.example.ruo.pojo.Therapy.EditTherapyResponse;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TherapyActivityEdit extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_CODE = 123;
    private static final int PICK_IMAGE_REQUEST = 1;
    String imagePath;

    APITherapy apiTherapy;

    private boolean userSelectedNewImage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapy_edit);

        ImageView btnBackEditTherapy = findViewById(R.id.btnBackEditTherapy);
       btnBackEditTherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TherapyActivity2.class);
                startActivity(intent);
            }
        });

        TextInputLayout nameLayout = findViewById(R.id.editNamaTherapy);
        TextInputEditText nameInput = (TextInputEditText) nameLayout.getEditText();

        TextInputLayout spesialistLayout = findViewById(R.id.editSpesialistTherapy);
        TextInputEditText spesialistInput = (TextInputEditText) spesialistLayout.getEditText();

        TextInputLayout lamaKerjaLayout = findViewById(R.id.editLamaKerjaTherapy);
        TextInputEditText lamaKerjaInput = (TextInputEditText) lamaKerjaLayout.getEditText();

        TextInputLayout noTelpLayout = findViewById(R.id.editNoTelpTherapy);
        TextInputEditText noTelpInput = (TextInputEditText) noTelpLayout.getEditText();

        TextInputLayout instagramLayout = findViewById(R.id.editInstagramTherapy);
        TextInputEditText instagramInput = (TextInputEditText) instagramLayout.getEditText();

        ImageView fotoPsikologImg = findViewById(R.id.fotoPsikologEdit);

        Intent intent = getIntent();
        if (intent != null) {
            int therapyId = intent.getIntExtra("therapyId", 0);

            apiTherapy = APIClient.getClient().create(APITherapy.class);
            SharedPreferences sharedPref = getSharedPreferences("env", Context.MODE_PRIVATE);
            String authToken = "Bearer " + sharedPref.getString("ACCESS_TOKEN_SECRET", null);
            Integer id_user = sharedPref.getInt("id_user", 0);

            if (authToken != null){
                Call<DetailTherapyResponse> call = apiTherapy.getDetailTherapyResp(authToken, therapyId);
                call.enqueue(new Callback<DetailTherapyResponse>() {
                    @Override
                    public void onResponse(Call<DetailTherapyResponse> call, Response<DetailTherapyResponse> response) {
                        if (response.isSuccessful()){
                            nameInput.setText(response.body().getDataTherapy().getNamaPsikolog());
                            spesialistInput.setText(response.body().getDataTherapy().getSpesialisPsikolog());
                            lamaKerjaInput.setText(Integer.toString(response.body().getDataTherapy().getLamaKarir()));
                            noTelpInput.setText(response.body().getDataTherapy().getNoTelpPsikolog());
                            instagramInput.setText(response.body().getDataTherapy().getMedsosPsikolog());
                            String fotoPsikolog = response.body().getDataTherapy().getFotoPsikolog();
                            String baseUrl = "http://10.0.2.2:3000";
                            String photoUrl = baseUrl + "/fotoPsikolog/" + fotoPsikolog;
                            imagePath = photoUrl;
                            Picasso.get().load(photoUrl).into(fotoPsikologImg);


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

                            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(TherapyActivityEdit.this);
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
                    public void onFailure(Call<DetailTherapyResponse> call, Throwable t) {
                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(TherapyActivityEdit.this);
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

        Button btnImageTherapy = findViewById(R.id.buttonEditImageTherapy);
        btnImageTherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Periksa izin sebelum memulai intent pemilihan gambar
                if (ContextCompat.checkSelfPermission(TherapyActivityEdit.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Jika izin belum diberikan, minta izin kepada pengguna
                    ActivityCompat.requestPermissions(TherapyActivityEdit.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSION_CODE);
                } else {
                    // Jika izin sudah diberikan, lanjutkan dengan operasi pemilihan gambar
                    startImageSelection();
                }
            }
        });

        Button btnEditTherapy = findViewById(R.id.btnEditDataTherapy);
        btnEditTherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameInput.getText().toString().trim();
                String spesialist = spesialistInput.getText().toString().trim();
                String lamaKerja = lamaKerjaInput.getText().toString().trim();
                String noTelp = noTelpInput.getText().toString().trim();
                String instagram = instagramInput.getText().toString().trim();

                Intent intent = getIntent();
                if (intent != null) {
                    int therapyId = intent.getIntExtra("therapyId", 0);

                    File file = new File(imagePath);
                    Log.d("file", "onClick: " + file);
                    RequestBody requsetFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requsetFile);

                    RequestBody request_nama = RequestBody.create(MediaType.parse("multipart/form-data"), name);
                    RequestBody request_lama_karir = RequestBody.create(MediaType.parse("multipart/form-data"), lamaKerja);
                    RequestBody request_no_telp = RequestBody.create(MediaType.parse("multipart/form-data"), noTelp);
                    RequestBody request_medsos = RequestBody.create(MediaType.parse("multipart/form-data"), instagram);
                    RequestBody request_spesialis = RequestBody.create(MediaType.parse("multipart/form-data"), spesialist);

                    apiTherapy = APIClient.getClient().create(APITherapy.class);
                    SharedPreferences sharedPref = getSharedPreferences("env", Context.MODE_PRIVATE);
                    String authToken = "Bearer " + sharedPref.getString("ACCESS_TOKEN_SECRET", null);
                    Integer id_user = sharedPref.getInt("id_user", 0);

                    if (authToken != null){
                        if (userSelectedNewImage == true){
                            Call<EditTherapyResponse> call = apiTherapy.getEditTherapyResp(authToken, therapyId, body, request_nama, request_lama_karir, request_no_telp, request_medsos, request_spesialis);
                            call.enqueue(new Callback<EditTherapyResponse>() {
                                @Override
                                public void onResponse(Call<EditTherapyResponse> call, Response<EditTherapyResponse> response) {
                                    if (response.isSuccessful()){
                                        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(TherapyActivityEdit.this);
                                        materialAlertDialogBuilder
                                                .setMessage(response.body().getMessage())
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Intent intent = new Intent(getApplicationContext(), TherapyActivity2.class);
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

                                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(TherapyActivityEdit.this);
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
                                public void onFailure(Call<EditTherapyResponse> call, Throwable t) {
                                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(TherapyActivityEdit.this);
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
                            Call<EditTherapyResponse> call = apiTherapy.getEditTherapyRespNo(authToken, therapyId, request_nama, request_lama_karir, request_no_telp, request_medsos, request_spesialis);
                            call.enqueue(new Callback<EditTherapyResponse>() {
                                @Override
                                public void onResponse(Call<EditTherapyResponse> call, Response<EditTherapyResponse> response) {
                                    if (response.isSuccessful()){
                                        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(TherapyActivityEdit.this);
                                        materialAlertDialogBuilder
                                                .setMessage(response.body().getMessage())
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Intent intent = new Intent(getApplicationContext(), TherapyActivity2.class);
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

                                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(TherapyActivityEdit.this);
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
                                public void onFailure(Call<EditTherapyResponse> call, Throwable t) {
                                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(TherapyActivityEdit.this);
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


                    } else {
                        Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent1);
                    }
                } else {
                    Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent1);
                }
            }
        });

    }

    private void startImageSelection() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //  request code yang dikirimkan saat memulai aktivitas adalah PICK_IMAGE_REQUEST
        if (requestCode == PICK_IMAGE_REQUEST) {
            //hasil operasi pemilihan gambar berhasil dan data yang diterima tidak null
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri imageUri = data.getData();
                Context context = TherapyActivityEdit.this;
                imagePath = RealPathUtil.getRealPath(context, imageUri);

                // Tambahkan log untuk mengecek tipe mime file yang dipilih
                String mimeType = getContentResolver().getType(imageUri);
                Log.d("TAG", "Selected file MIME type: " + mimeType);

                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                ImageView fotoPsikolog = findViewById(R.id.fotoPsikologEdit);
                fotoPsikolog.setImageURI(imageUri);
                userSelectedNewImage = true;
            } else {
                Toast.makeText(this, "Image selection canceled", Toast.LENGTH_SHORT).show();
            }
        }

    }
}