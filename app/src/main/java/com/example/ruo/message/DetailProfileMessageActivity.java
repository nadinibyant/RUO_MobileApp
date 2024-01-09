package com.example.ruo.message;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.ruo.API.APIMessage;
import com.example.ruo.APIClient;
import com.example.ruo.R;
import com.example.ruo.pojo.message.AccountItem;
import com.example.ruo.pojo.message.AccountResponse;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProfileMessageActivity extends AppCompatActivity {

    int messageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profile_message);
        messageId = getIntent().getIntExtra("messageId", -1);

        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(view -> finish());

        if (messageId != -1) {
            APIMessage apiMessage = APIClient.getClient().create(APIMessage.class);
            SharedPreferences sharedPref = getSharedPreferences("env", Context.MODE_PRIVATE);
            String authToken = "Bearer " + sharedPref.getString("ACCESS_TOKEN_SECRET", null);
            Call<AccountResponse> call = apiMessage.getAccount(authToken, messageId);
            call.enqueue(new Callback<AccountResponse>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NonNull Call<AccountResponse> call,
                    @NonNull Response<AccountResponse> response) {
                    if (response.isSuccessful()) {
                        AccountItem data = response.body().getDataUser();
                        if (data == null) {
                            LinearLayout llContentProfile = findViewById(R.id.llContentProfile);
                            TextView tvHideProfile = findViewById(R.id.tvHideProfile);
                            CircleImageView ivDetailProfile = findViewById(R.id.ivDetailProfile);
                            ivDetailProfile.setVisibility(View.GONE);
                            llContentProfile.setVisibility(View.GONE);
                            tvHideProfile.setVisibility(View.VISIBLE);
                            TextView tvProfileName = findViewById(R.id.tvProfileName);
                            tvProfileName.setText("xxxxxxxx" + " PROFILE's");
                        } else {
                            LinearLayout llContentProfile = findViewById(R.id.llContentProfile);
                            TextView tvHideProfile = findViewById(R.id.tvHideProfile);
                            CircleImageView ivDetailProfile = findViewById(R.id.ivDetailProfile);
                            ivDetailProfile.setVisibility(View.VISIBLE);
                            llContentProfile.setVisibility(View.VISIBLE);
                            tvHideProfile.setVisibility(View.GONE);
                            TextView tvProfileName = findViewById(R.id.tvProfileName);
                            TextView tvUsername = findViewById(R.id.tvUsername);
                            TextView tvEmail = findViewById(R.id.tvEmail);
                            TextView tvInstagram = findViewById(R.id.tvInstagram);
                            TextView tvDescription = findViewById(R.id.tvDescription);
                            tvProfileName.setText(data.getUsername() + " PROFILE's");
                            Picasso.get().load(data.getFotoUser()).into(ivDetailProfile);
                            tvUsername.setText("Username: " + data.getUsername());
                            tvEmail.setText("Email: " + data.getEmail());
                            if (data.getMedsos() == null) {
                                tvInstagram.setText("Tidak ada medsos");
                            } else {
                                tvInstagram.setText(data.getMedsos());
                            }
                            tvDescription.setText("Deskripsi: " + data.getDescription());
                        }

                    } else {
                        Toast.makeText(DetailProfileMessageActivity.this,
                            "Terjadi kesalahan!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<AccountResponse> call, @NonNull Throwable t) {
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(
                        DetailProfileMessageActivity.this);
                    builder
                        .setMessage(t.getMessage())
                        .setPositiveButton("OK", (dialog, which) -> {
                            // Tindakan yang diambil ketika tombol "OK" diklik
                            dialog.dismiss();
                        })
                        .show();
                }
            });
        }
    }
}