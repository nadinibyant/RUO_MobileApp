package com.example.ruo.message;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ruo.API.APIMessage;
import com.example.ruo.APIClient;
import com.example.ruo.R;
import com.example.ruo.pojo.message.CommentMessageResponse;
import com.example.ruo.pojo.message.DetailMessageResponse;
import com.example.ruo.pojo.message.GeneralResponse;
import com.example.ruo.pojo.message.MessageResponse;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailContentMessageActivity extends AppCompatActivity {

    private int idMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_content_message);
        idMessage = getIntent().getIntExtra("idMessage", -1);
        getDetailMessage();
        Button btnSendComment = findViewById(R.id.btnSendComment);
        EditText etAddComment = findViewById(R.id.etAddComment);
        btnSendComment.setOnClickListener(view -> {
            if (etAddComment.getText().toString().trim().isEmpty()) return;
            APIMessage apiMessage = APIClient.getClient().create(APIMessage.class);
            SharedPreferences sharedPref = getSharedPreferences("env", Context.MODE_PRIVATE);
            String authToken = "Bearer " + sharedPref.getString("ACCESS_TOKEN_SECRET", null);
            Integer userId = sharedPref.getInt("id_user", 0);
            Call<GeneralResponse> call = apiMessage.addComment(authToken, idMessage, userId, etAddComment.getText().toString().trim());
            call.enqueue(new Callback<GeneralResponse>() {
                @Override
                public void onResponse(@NonNull Call<GeneralResponse> call,
                    @NonNull Response<GeneralResponse> response) {
                    if (response.isSuccessful()) {
                        getAllComments();
                        etAddComment.setText("");
                    } else {
                        Toast.makeText(DetailContentMessageActivity.this,
                            "Gagal kirim comment!",
                            Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GeneralResponse> call, Throwable t) {
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(
                        DetailContentMessageActivity.this);
                    builder
                        .setMessage(t.getMessage())
                        .setPositiveButton("OK", (dialog, which) -> {
                            // Tindakan yang diambil ketika tombol "OK" diklik
                            dialog.dismiss();
                        })
                        .show();
                }
            });
        });
    }

    private void getDetailMessage() {
        APIMessage apiMessage = APIClient.getClient().create(APIMessage.class);
        SharedPreferences sharedPref = getSharedPreferences("env", Context.MODE_PRIVATE);
        String authToken = "Bearer " + sharedPref.getString("ACCESS_TOKEN_SECRET", null);
        Call<DetailMessageResponse> call = apiMessage.detailMessage(authToken, idMessage);
        call.enqueue(new Callback<DetailMessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<DetailMessageResponse> call,
                @NonNull Response<DetailMessageResponse> response) {
                if (response.isSuccessful()) {
                    MessageResponse data = response.body().getDetailMessage();
                    TextView tvName = findViewById(R.id.tvName);
                    TextView tvDetailMessage = findViewById(R.id.tvDetailMessage);
                    TextView tvTimestamp = findViewById(R.id.tvTimestamp);
                    tvName.setText(String.valueOf(data.getIdUser()));
                    tvDetailMessage.setText(data.getIsiMessage());
                    ConvertDate convertDate = new ConvertDate();
                    tvTimestamp.setText(convertDate.convertDate(data.getCreatedAt()));
                    getAllComments();
                } else {
                    Toast.makeText(DetailContentMessageActivity.this,
                        "Tidak ada detail message!",
                        Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailMessageResponse> call, Throwable t) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(
                    DetailContentMessageActivity.this);
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

    private void getAllComments() {
        APIMessage apiMessage = APIClient.getClient().create(APIMessage.class);
        SharedPreferences sharedPref = getSharedPreferences("env", Context.MODE_PRIVATE);
        String authToken = "Bearer " + sharedPref.getString("ACCESS_TOKEN_SECRET", null);
        Call<CommentMessageResponse> callComment = apiMessage.commentMessage(authToken, idMessage);
        callComment.enqueue(new Callback<CommentMessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommentMessageResponse> call,
                @NonNull Response<CommentMessageResponse> response) {
                if (response.isSuccessful()) {
                    RecyclerView rvComment = findViewById(R.id.rvComments);
                    rvComment.setLayoutManager(
                        new LinearLayoutManager(DetailContentMessageActivity.this));
                    rvComment.setAdapter(new CommentAdapter(DetailContentMessageActivity.this,
                        response.body().getDataComment()));
                } else {
                    Toast.makeText(DetailContentMessageActivity.this,
                        "Tidak ada comment!",
                        Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommentMessageResponse> call, Throwable t) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(
                    DetailContentMessageActivity.this);
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