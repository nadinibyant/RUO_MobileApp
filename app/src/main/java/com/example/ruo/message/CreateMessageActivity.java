package com.example.ruo.message;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.ruo.API.APIMessage;
import com.example.ruo.APIClient;
import com.example.ruo.R;
import com.example.ruo.pojo.message.GeneralResponse;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateMessageActivity extends AppCompatActivity {

    EditText etMyMessage;
    Button btnSend;

    TextView tvAlertYes, tvAlertNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);
        etMyMessage = findViewById(R.id.etMyMessage);
        btnSend = findViewById(R.id.btnSend);
        tvAlertYes = findViewById(R.id.tvAlertYes);
        tvAlertNo = findViewById(R.id.tvAlertNo);
        setTodayDate();
        setListener();
    }

    private void setListener() {
        btnSend.setOnClickListener(view -> {
            if (etMyMessage.getText().toString().trim().isEmpty()) {
                Toast.makeText(CreateMessageActivity.this, "Pesan tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            } else {
                ConstraintLayout clDialog = findViewById(R.id.clDialog);
                clDialog.setVisibility(View.VISIBLE);
            }
        });

        tvAlertYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMessageToAPI(true);
            }
        });

        tvAlertNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMessageToAPI(false);
            }
        });
    }

    private void addMessageToAPI(boolean isHide) {
        APIMessage apiMessage = APIClient.getClient().create(APIMessage.class);
        SharedPreferences sharedPref = getSharedPreferences("env", Context.MODE_PRIVATE);
        String authToken = "Bearer " + sharedPref.getString("ACCESS_TOKEN_SECRET", null);
        int userId = sharedPref.getInt("id_user", 0);
        Call<GeneralResponse> call = apiMessage.addMessage(authToken, userId, etMyMessage.getText().toString().trim(), isHide);
        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(@NonNull Call<GeneralResponse> call,
                @NonNull Response<GeneralResponse> response) {
                if (response.isSuccessful()) {
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(
                        CreateMessageActivity.this);
                    builder
                        .setMessage(response.body().getMessage())
                        .setPositiveButton("OK", (dialog, which) -> {
                            // Tindakan yang diambil ketika tombol "OK" diklik
                            dialog.dismiss();
                            CreateMessageActivity.this.finish();
                        })
                        .show();
                } else {
                    Toast.makeText(CreateMessageActivity.this,
                        "Terjadi kesalahan!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(
                    CreateMessageActivity.this);
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

    private void setTodayDate() {
        TextView tvDate = findViewById(R.id.tvDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID"));
        String formattedDate = dateFormat.format(new Date());
        tvDate.setText(formattedDate);
    }
}