package com.example.ruo.Therapy;

import androidx.annotation.NonNull;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ruo.API.APITherapy;
import com.example.ruo.APIClient;
import com.example.ruo.Home.HomeActivity1;
import com.example.ruo.Home.HomeActivity3;
import com.example.ruo.LoginActivity;
import com.example.ruo.Profile.ProfileActivity;
import com.example.ruo.R;
import com.example.ruo.message.MessageActivity;
import com.example.ruo.pojo.Therapy.DataTherapy;
import com.example.ruo.pojo.Therapy.DetailTherapyResponse;
import com.example.ruo.pojo.Therapy.EditTherapyResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
        profileItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                return true;
            }
        });

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

        TextInputLayout alamatLayout = findViewById(R.id.inputAlamatLengkapTherapy);
        TextInputEditText alamatInput = (TextInputEditText) alamatLayout.getEditText();

        ImageView fotoPsikologImg = findViewById(R.id.fotoPsikologEdit);

        Intent intent = getIntent();
        if (intent != null) {
            int therapyId = intent.getIntExtra("therapyId", 0);

            apiTherapy = APIClient.getClient().create(APITherapy.class);
            SharedPreferences sharedPref = getSharedPreferences("env", Context.MODE_PRIVATE);
            String authToken = "Bearer " + sharedPref.getString("ACCESS_TOKEN_SECRET", null);
            String Token = sharedPref.getString("ACCESS_TOKEN_SECRET", null);
            Integer id_user = sharedPref.getInt("id_user", 0);

            if (Token != null){
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
                            alamatInput.setText(response.body().getDataTherapy().getAlamatLengkap());
                            String fotoPsikolog = response.body().getDataTherapy().getFotoPsikolog();
                            String baseUrl = "https://2nhj8ts5-3000.use.devtunnels.ms/";
                            String photoUrl = baseUrl + "fotoPsikolog/" + fotoPsikolog;
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
//                Periksa izin sebelum memulai intent pemilihan gambar
                if (ContextCompat.checkSelfPermission(TherapyActivityEdit.this,
                        Manifest.permission.READ_MEDIA_IMAGES)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Jika izin belum diberikan, minta izin kepada pengguna
                    ActivityCompat.requestPermissions(TherapyActivityEdit.this,
                            new String[]{Manifest.permission.READ_MEDIA_IMAGES},
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
                String alamat = alamatInput.getText().toString().trim();

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
                    RequestBody request_alamat = RequestBody.create(MediaType.parse("multipart/form-data"), alamat);

                    apiTherapy = APIClient.getClient().create(APITherapy.class);
                    SharedPreferences sharedPref = getSharedPreferences("env", Context.MODE_PRIVATE);
                    String authToken = "Bearer " + sharedPref.getString("ACCESS_TOKEN_SECRET", null);
                    String Token = sharedPref.getString("ACCESS_TOKEN_SECRET", null);
                    Integer id_user = sharedPref.getInt("id_user", 0);

                    if (Token != null){
                        if (userSelectedNewImage == true){
                            Call<EditTherapyResponse> call = apiTherapy.getEditTherapyResp(authToken, therapyId, body, request_nama, request_lama_karir, request_no_telp, request_medsos, request_spesialis, request_alamat);
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
                            Call<EditTherapyResponse> call = apiTherapy.getEditTherapyRespNo(authToken, therapyId, request_nama, request_lama_karir, request_no_telp, request_medsos, request_spesialis, request_alamat);
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