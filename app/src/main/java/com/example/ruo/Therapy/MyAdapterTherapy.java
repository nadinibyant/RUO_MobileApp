package com.example.ruo.Therapy;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruo.API.APITherapy;
import com.example.ruo.APIClient;
import com.example.ruo.LoginActivity;
import com.example.ruo.R;
import com.example.ruo.peta.petaView;
import com.example.ruo.pojo.Therapy.AllTherapyItem;
import com.example.ruo.pojo.Therapy.DelOtomatisResponse;
import com.example.ruo.pojo.Therapy.DislikeResponse;
import com.example.ruo.pojo.Therapy.FindLikeResponse;
import com.example.ruo.pojo.Therapy.LikeResponse;
import com.example.ruo.pojo.peta.PetaResponse;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdapterTherapy extends RecyclerView.Adapter<MyAdapterTherapy.ViewHolder> {

    Context context;

    private static final String CHANNEL_ID = "data_therapy";
    private static final String CHANNEL_NAME = "data_therapy";
    private static final String CHANNEL_DESC = "data_therapy";

    APITherapy apiTherapy;
    public AllTherapyItem[] therapyData;

    public MyAdapterTherapy(Context applicationContext, ArrayList<AllTherapyItem> therapyData) {
        this.context = applicationContext;
        this.therapyData = therapyData.toArray(new AllTherapyItem[0]);
    }


    @NonNull
    @Override
//    nentuin layout xml mana yang dipake untuk list data nya
    public MyAdapterTherapy.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_list_teraphy, parent, false);
        return new ViewHolder(view);
    }

    @Override
//    nge set nilai per tag xml nya, dan ambil data dari MylisttherapyData
    public void onBindViewHolder(@NonNull MyAdapterTherapy.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String fotoPsikolog = therapyData[position].getFotoPsikolog();

        // Periksa apakah fotoPsikolog adalah URL langsung atau hanya nama file
        if (fotoPsikolog.startsWith("http")) {
            Picasso.get().load(fotoPsikolog).into(holder.imgPsikolog);
        } else {
            // Jika fotoPsikolog adalah nama file,  URL dari server
            String baseUrl = "https://2nhj8ts5-3000.use.devtunnels.ms/";
            String photoUrl = baseUrl + "fotoPsikolog/" + fotoPsikolog;
            Picasso.get().load(photoUrl).fit().into(holder.imgPsikolog);
        }

        holder.nama.setText(therapyData[position].getNamaPsikolog());
        holder.pekerjaan.setText(therapyData[position].getSpesialisPsikolog());
        holder.lama_kerja.setText(Integer.toString(therapyData[position].getLamaKarir()));
        holder.instagram.setText(therapyData[position].getMedsosPsikolog());
        holder.no_telp.setText(therapyData[position].getNoTelpPsikolog());

        holder.imgLike.setImageResource(therapyData[position].getImgLike());
        holder.imgLike.setTag(therapyData[position].getIdTherapy());
        holder.imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int therapyId = (int) view.getTag();
                apiTherapy = APIClient.getClient().create(APITherapy.class);
                SharedPreferences sharedPref = context.getSharedPreferences("env", Context.MODE_PRIVATE);
                String authToken = "Bearer " + sharedPref.getString("ACCESS_TOKEN_SECRET", null);
                Integer id_user = sharedPref.getInt("id_user", 0);

                if (authToken != null){
                    Call<LikeResponse> call = apiTherapy.getLikeResp(authToken, therapyId, id_user);
                    call.enqueue(new Callback<LikeResponse>() {
                        @Override
                        public void onResponse(Call<LikeResponse> call, Response<LikeResponse> response) {
                            Integer likeSummary = therapyData[position].getLike();
                            if (response.isSuccessful()){
                                if (response.body().getMessage().contains("successfully liked")) {
                                    likeSummary++;
                                    holder.jumlahLike.setText(Integer.toString((likeSummary)));
                                } else {
                                    holder.jumlahLike.setText(Integer.toString((likeSummary)));
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

                                Toast.makeText(view.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<LikeResponse> call, Throwable t) {
                            Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Intent intent = new Intent(view.getContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getContext().startActivity(intent);
                }

            }
        });

        holder.imgDislike.setImageResource(therapyData[position].getImgDislike());
        holder.imgDislike.setTag(therapyData[position].getIdTherapy());
        holder.imgDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int therapyId = (int) view.getTag();
                apiTherapy = APIClient.getClient().create(APITherapy.class);
                SharedPreferences sharedPref = context.getSharedPreferences("env", Context.MODE_PRIVATE);
                String authToken = "Bearer " + sharedPref.getString("ACCESS_TOKEN_SECRET", null);
                Integer id_user = sharedPref.getInt("id_user", 0);

                if (authToken != null) {
                    Call<DislikeResponse> call = apiTherapy.getDislikeResp(authToken, therapyId,id_user);
                    call.enqueue(new Callback<DislikeResponse>() {
                        @Override
                        public void onResponse(Call<DislikeResponse> call, Response<DislikeResponse> response) {
                            if (response.isSuccessful()){
                                Log.d("TAG", "onResponse: berhasil");
                                Integer dislikeSummary = therapyData[position].getDislike();
                                if (response.body().getMessage().contains("Successfully added dislike")) {
                                    dislikeSummary++;
                                    holder.jumlahUnlike.setText(Integer.toString((dislikeSummary)));
                                } else if (response.body().getMessage().contains("therapy data was deleted because dislikes exceeded 10")){
                                    Toast.makeText(view.getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(view.getContext(), TherapyActivity1.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Tambahkan flag ini
                                    view.getContext().startActivity(intent);
                                } else {
                                    holder.jumlahUnlike.setText(Integer.toString((dislikeSummary)));
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

                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<DislikeResponse> call, Throwable t) {
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Intent intent = new Intent(view.getContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getContext().startActivity(intent);
                }
            }
        });
        holder.jumlahLike.setText(Integer.toString(therapyData[position].getLike()));
        holder.jumlahUnlike.setText(Integer.toString(therapyData[position].getDislike()));
        holder.imgAlamat.setImageResource(R.drawable.baseline_location_on_24);
        holder.imgAlamat.setTag(therapyData[position].getIdTherapy());
        holder.imgAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int therapyId = (int) view.getTag();
                Intent intent = new Intent(holder.itemView.getContext(), petaView.class);
                intent.putExtra("therapyId", therapyId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return therapyData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        ngedeteksi tag xml yg digunakan
        ImageView imgPsikolog;
        TextView nama;
        TextView pekerjaan;
        TextView lama_kerja;
        TextView no_telp;
        TextView instagram;
        ImageView imgLike;
        ImageView imgDislike;
        TextView jumlahLike;
        TextView jumlahUnlike;

        ImageView imgAlamat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPsikolog = itemView.findViewById(R.id.imgPsikolog);
            nama = itemView.findViewById(R.id.nama);
            pekerjaan = itemView.findViewById(R.id.pekerjaan);
            lama_kerja = itemView.findViewById(R.id.lama_kerja);
            no_telp = itemView.findViewById(R.id.no_telp_therapy);
            instagram = itemView.findViewById(R.id.instagram_therapy);
            imgLike = itemView.findViewById(R.id.imgLike);
            imgDislike = itemView.findViewById(R.id.imgDislike);
            jumlahLike = itemView.findViewById(R.id.jumlah_like);
            jumlahUnlike = itemView.findViewById(R.id.jumlah_unlike);
            imgAlamat = itemView.findViewById(R.id.imgAlamat);
        }
    }

}
