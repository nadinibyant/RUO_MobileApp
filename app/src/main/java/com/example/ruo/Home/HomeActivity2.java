package com.example.ruo.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ruo.API.APIChatTerry;
import com.example.ruo.APIClient;
import com.example.ruo.LoginActivity;
import com.example.ruo.R;
import com.example.ruo.Therapy.TherapyActivity1;
import com.example.ruo.pojo.chatTerry.Answer1Response;
import com.example.ruo.pojo.chatTerry.QuestionItem;
import com.example.ruo.pojo.chatTerry.QuestionResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity2 extends AppCompatActivity {

    APIChatTerry apichatterry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        MenuItem homeItem = bottomNavigationView.getMenu().findItem(R.id.item_home);
        homeItem.setChecked(true);

        homeItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent inten = new Intent(getApplicationContext(), HomeActivity1.class);
                startActivity(inten);
                return true;
            }
        });

        MenuItem therapyItem = bottomNavigationView.getMenu().findItem(R.id.item_therapy);
        therapyItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent intent = new Intent(getApplicationContext(), TherapyActivity1.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                return true;
            }
        });

        TextInputLayout answer1Layout = findViewById(R.id.answer1);
        TextInputEditText answer1Input = (TextInputEditText) answer1Layout.getEditText();

        apichatterry = APIClient.getClient().create(APIChatTerry.class);
        SharedPreferences sharedPref = getSharedPreferences("env", Context.MODE_PRIVATE);
        String authToken = "Bearer " + sharedPref.getString("ACCESS_TOKEN_SECRET", null);

        if (authToken != null) {
            Call<QuestionResponse> call = apichatterry.getQuestResp(authToken);
            call.enqueue(new Callback<QuestionResponse>() {
                @Override
                public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                    if (response.isSuccessful()){
                        List<QuestionItem> questionItems = response.body().getQuestion();

                        if (questionItems != null && questionItems.size() > 0){
                            String question = questionItems.get(0).getPertanyaan();
                            TextView textView6 = findViewById(R.id.textView6);
                            textView6.setText(question);
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

                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(HomeActivity2.this);
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
                public void onFailure(Call<QuestionResponse> call, Throwable t) {
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(HomeActivity2.this);
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

        Button btnSendAnswer1 = findViewById(R.id.buttonSendAnswer1);
        btnSendAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer1 = answer1Input.getText().toString().trim();
                if (answer1.isEmpty()){
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(HomeActivity2.this);
                                builder
                                        .setMessage("Please answer questions first")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // Tindakan yang diambil ketika tombol "OK" diklik
                                                dialog.dismiss();
                                            }
                                        })
                                        .show();
                } else {
                    Integer id_user = sharedPref.getInt("id_user", 0);
                    if (authToken != null){
                        Call<Answer1Response> call = apichatterry.getAnswer1Resp(authToken, answer1, id_user);
                        String finalAnswer = answer1;
                        call.enqueue(new Callback<Answer1Response>() {
                            @Override
                            public void onResponse(Call<Answer1Response> call, Response<Answer1Response> response) {
                                if (response.isSuccessful()){
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity3.class);
                                    intent.putExtra("answer1", finalAnswer);
                                    startActivity(intent);
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

                                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(HomeActivity2.this);
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
                            public void onFailure(Call<Answer1Response> call, Throwable t) {
                                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(HomeActivity2.this);
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
}