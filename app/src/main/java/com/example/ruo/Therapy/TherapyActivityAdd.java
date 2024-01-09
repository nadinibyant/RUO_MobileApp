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
import android.database.Cursor;
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

import com.example.ruo.API.APIChatTerry;
import com.example.ruo.API.APITherapy;
import com.example.ruo.APIClient;
import com.example.ruo.Home.HomeActivity1;
import com.example.ruo.Home.HomeActivity2;
import com.example.ruo.Home.HomeActivity3;
import com.example.ruo.LoginActivity;
import com.example.ruo.Profile.ProfileActivity;
import com.example.ruo.R;
import com.example.ruo.message.MessageActivity;
import com.example.ruo.pojo.Therapy.AddTherapyResponse;
import com.example.ruo.pojo.chatTerry.Answer1Response;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import android.provider.MediaStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TherapyActivityAdd extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    String imagePath;
    APITherapy apiTherapy;
    private static final int REQUEST_PERMISSION_CODE = 123;

    private static final int PICK_IMAGE_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapy_add);

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


        ImageView btnBackAddTherapy = findViewById(R.id.btnBackTherapyAdd);
        btnBackAddTherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TherapyActivity2.class);
                startActivity(intent);
            }
        });

        TextInputLayout nameLayout = findViewById(R.id.inputNamaTherapy);
        TextInputEditText nameInput = (TextInputEditText) nameLayout.getEditText();

        TextInputLayout spesialistLayout = findViewById(R.id.inputSpesialistTherapy);
        TextInputEditText spesialistInput = (TextInputEditText) spesialistLayout.getEditText();

        TextInputLayout lamaKerjaLayout = findViewById(R.id.inputLamaKerjaTherapy);
        TextInputEditText lamaKerjaInput = (TextInputEditText) lamaKerjaLayout.getEditText();

        TextInputLayout noTelpLayout = findViewById(R.id.inputNoTelpTherapy);
        TextInputEditText noTelpInput = (TextInputEditText) noTelpLayout.getEditText();

        TextInputLayout instagramLayout = findViewById(R.id.inputInstagramTherapy);
        TextInputEditText instagramInput = (TextInputEditText) instagramLayout.getEditText();

        TextInputLayout alamatLayout = findViewById(R.id.inputAlamatLengkapTherapy);
        TextInputEditText alamatInput = (TextInputEditText) alamatLayout.getEditText();


        Button btnAddImage = findViewById(R.id.buttonAddImageTherapy);
        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                 Periksa izin sebelum memulai intent pemilihan gambar
                if (ContextCompat.checkSelfPermission(TherapyActivityAdd.this,
                        Manifest.permission.READ_MEDIA_IMAGES)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Jika izin belum diberikan, minta izin kepada pengguna
                    ActivityCompat.requestPermissions(TherapyActivityAdd.this,
                            new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                            REQUEST_PERMISSION_CODE);
                } else {
                    // Jika izin sudah diberikan, lanjutkan dengan operasi pemilihan gambar
                    startImageSelection();
                }
            }
        });



        Button btnAddTherapy = findViewById(R.id.btnAddDataTherapy);
        btnAddTherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameInput.getText().toString().trim();
                String spesialist = spesialistInput.getText().toString().trim();
                String lamaKerja = lamaKerjaInput.getText().toString().trim();
                String noTelp = noTelpInput.getText().toString().trim();
                String instagram = instagramInput.getText().toString().trim();
                String alamat = alamatInput.getText().toString().trim();

                if (name.isEmpty()){
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(TherapyActivityAdd.this);
                    materialAlertDialogBuilder
                            .setMessage("Please Complete Your Name Data")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                } else if (spesialist.isEmpty()){
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(TherapyActivityAdd.this);
                    materialAlertDialogBuilder
                            .setMessage("Please Complete Your Spesialist Data")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                } else if (lamaKerja.isEmpty()){
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(TherapyActivityAdd.this);
                    materialAlertDialogBuilder
                            .setMessage("Please Complete Your Long Career Data")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                } else if (noTelp.isEmpty()){
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(TherapyActivityAdd.this);
                    materialAlertDialogBuilder
                            .setMessage("Please Complete Your No Phone Data")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                } else if (instagram.isEmpty()){
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(TherapyActivityAdd.this);
                    materialAlertDialogBuilder
                            .setMessage("Please Complete Your Instagram Data")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                } else if (alamat.isEmpty()){
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(TherapyActivityAdd.this);
                    materialAlertDialogBuilder
                            .setMessage("Please Complete Your Address Data")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                } else {
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
                    Integer id_user = sharedPref.getInt("id_user", 0);

                    if(authToken != null) {
                        Call<AddTherapyResponse> call = apiTherapy.getAddTherapyResp(authToken, id_user, body, request_nama, request_lama_karir, request_no_telp, request_medsos, request_spesialis, request_alamat);
                        call.enqueue(new Callback<AddTherapyResponse>() {
                            @Override
                            public void onResponse(Call<AddTherapyResponse> call, Response<AddTherapyResponse> response) {
                                if (response.isSuccessful()){
                                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(TherapyActivityAdd.this);
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

                                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(TherapyActivityAdd.this);
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
                            public void onFailure(Call<AddTherapyResponse> call, Throwable t) {
                                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(TherapyActivityAdd.this);
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
                Context context = TherapyActivityAdd.this;
                imagePath = RealPathUtil.getRealPath(context, imageUri);

                // Tambahkan log untuk mengecek tipe mime file yang dipilih
                String mimeType = getContentResolver().getType(imageUri);
                Log.d("TAG", "Selected file MIME type: " + mimeType);

                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                ImageView fotoPsikolog = findViewById(R.id.fotoPsikolog);
                fotoPsikolog.setImageURI(imageUri);
            } else {
                Toast.makeText(this, "Image selection canceled", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Izin diberikan, lanjutkan dengan operasi pemilihan gambar
                startImageSelection();
            } else {
                // Izin ditolak, berikan pesan ke pengguna atau ambil tindakan yang sesuai
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}