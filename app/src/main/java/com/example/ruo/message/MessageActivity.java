package com.example.ruo.message;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ruo.API.APIMessage;
import com.example.ruo.APIClient;
import com.example.ruo.R;
import com.example.ruo.pojo.message.GetAllMessageResponse;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        setTodayDate();
        getAllMessage();
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(
            view -> startActivity(new Intent(MessageActivity.this, CreateMessageActivity.class)));
    }

    private void getAllMessage() {
        RecyclerView rvMessage = findViewById(R.id.rvMessage);
        rvMessage.setLayoutManager(new LinearLayoutManager(this));

        APIMessage apiMessage = APIClient.getClient().create(APIMessage.class);
        SharedPreferences sharedPref = getSharedPreferences("env", Context.MODE_PRIVATE);
        String authToken = "Bearer " + sharedPref.getString("ACCESS_TOKEN_SECRET", null);
        Call<GetAllMessageResponse> call = apiMessage.getAllMessage(authToken);
        call.enqueue(new Callback<GetAllMessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetAllMessageResponse> call,
                @NonNull Response<GetAllMessageResponse> response) {
                if (response.isSuccessful()) {
                    rvMessage.setAdapter(new MessageAdapter(MessageActivity.this,
                        response.body().getAllMessageItems()));
                } else {
                    Toast.makeText(MessageActivity.this,
                        "Data kosong!" ,
                        Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetAllMessageResponse> call, Throwable t) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(
                    MessageActivity.this);
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

    @Override
    protected void onResume() {
        super.onResume();
        getAllMessage();
    }

    private void setTodayDate() {
        TextView tvDate = findViewById(R.id.tvDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID"));
        String formattedDate = dateFormat.format(new Date());
        tvDate.setText(formattedDate);
    }
}