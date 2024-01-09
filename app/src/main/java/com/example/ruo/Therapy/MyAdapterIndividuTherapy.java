package com.example.ruo.Therapy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruo.API.APITherapy;
import com.example.ruo.APIClient;
import com.example.ruo.LoginActivity;
import com.example.ruo.R;
import com.example.ruo.peta.petaView;
import com.example.ruo.pojo.Therapy.DeleteTherapyResponse;
import com.example.ruo.pojo.Therapy.MyTherapyItem;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdapterIndividuTherapy extends RecyclerView.Adapter<MyAdapterIndividuTherapy.ViewHolder> {

    Context context;

    APITherapy apiTherapy;

    public MyTherapyItem[] myTherapyItems;

    public MyAdapterIndividuTherapy(Context context, ArrayList<MyTherapyItem> myTherapyItems){
        this.context = context;
        this.myTherapyItems = myTherapyItems.toArray(new MyTherapyItem[0]);
    }

    @NonNull
    @Override
    public MyAdapterIndividuTherapy.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_single_list_individu_therapy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterIndividuTherapy.ViewHolder holder, int position) {
        String fotoPsikolog = myTherapyItems[position].getFotoPsikolog();
        int indexOfDot = fotoPsikolog.lastIndexOf(".");

        // Periksa apakah fotoPsikolog adalah URL langsung atau hanya nama file
        if (fotoPsikolog.startsWith("http")) {
            Picasso.get().load(fotoPsikolog).into(holder.imgPsikologIndividu);
        } else {
            // Jika fotoPsikolog adalah nama file,  URL dari server
            String baseUrl = "https://2nhj8ts5-3000.use.devtunnels.ms/";
            String photoUrl = baseUrl + "fotoPsikolog/" + fotoPsikolog;
            Picasso.get().load(photoUrl).fit().into(holder.imgPsikologIndividu);
        }
        holder.editTherapyIndividu.setTag(myTherapyItems[position].getIdTherapy());
        holder.editTherapyIndividu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int therapyId = (int) view.getTag();
                Intent intent = new Intent(holder.itemView.getContext(), TherapyActivityEdit.class);
                intent.putExtra("therapyId", therapyId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
        holder.deleteTherapyIndividu.setTag(myTherapyItems[position].getIdTherapy());
        holder.deleteTherapyIndividu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int therapyId = (int) view.getTag();
                        apiTherapy = APIClient.getClient().create(APITherapy.class);
                        SharedPreferences sharedPref = context.getSharedPreferences("env", Context.MODE_PRIVATE);
                        String authToken = "Bearer " + sharedPref.getString("ACCESS_TOKEN_SECRET", null);
                        Integer id_user = sharedPref.getInt("id_user", 0);

                        if (authToken != null) {
                            Call<DeleteTherapyResponse> call = apiTherapy.getDeleteTherapyResp(authToken, therapyId);
                            call.enqueue(new Callback<DeleteTherapyResponse>() {
                                @Override
                                public void onResponse(Call<DeleteTherapyResponse> call, Response<DeleteTherapyResponse> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(view.getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(view.getContext(), TherapyActivity2.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        view.getContext().startActivity(intent);
                                    } else {
                                        Toast.makeText(view.getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<DeleteTherapyResponse> call, Throwable t) {
                                    Log.e("API_CALL_ERROR", "API Call Failed", t);
                                }
                            });
                        } else {
                            Intent intent = new Intent(view.getContext(), LoginActivity.class);
                            view.getContext().startActivity(intent);
                        }
            }
        });
        holder.namaIndividu.setText(myTherapyItems[position].getNamaPsikolog());
        holder.pekerjaanIndividu.setText(myTherapyItems[position].getSpesialisPsikolog());
        holder.lamaKerjaIndividu.setText(Integer.toString(myTherapyItems[position].getLamaKarir()));
        holder.noTelpIndividu.setText(myTherapyItems[position].getNoTelpPsikolog());
        holder.instagramIndividu.setText(myTherapyItems[position].getMedsosPsikolog());
        holder.jumlahLikeIndividu.setText(Integer.toString(myTherapyItems[position].getLike()));
        holder.jumlahUnlikeIndividu.setText(Integer.toString(myTherapyItems[position].getDislike()));
        holder.editTherapyIndividu.setImageResource(myTherapyItems[position].getIdImgEdit());
        holder.deleteTherapyIndividu.setImageResource(myTherapyItems[position].getImgDel());
        holder.like.setImageResource(myTherapyItems[position].getImgLike());
        holder.unlike.setImageResource(myTherapyItems[position].getImgDislike());
        holder.imgAlamat.setImageResource(R.drawable.baseline_location_on_24);
        holder.imgAlamat.setTag(myTherapyItems[position].getIdTherapy());
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
        return myTherapyItems.length;
    }

//    public class StartGameDialogFragment extends DialogFragment {
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            // Use the Builder class for convenient dialog construction
//
//        }
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPsikologIndividu;
        ImageView editTherapyIndividu;
        ImageView deleteTherapyIndividu;
        TextView namaIndividu;
        TextView pekerjaanIndividu;
        TextView lamaKerjaIndividu;
        TextView noTelpIndividu;
        TextView instagramIndividu;
        TextView jumlahLikeIndividu;
        TextView jumlahUnlikeIndividu;

        ImageView like;

        ImageView unlike;

        ImageView imgAlamat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPsikologIndividu = itemView.findViewById(R.id.imgPsikologIndividu);
            editTherapyIndividu = itemView.findViewById(R.id.editTherapyIndividu);
            deleteTherapyIndividu = itemView.findViewById(R.id.deleteTherapyIndividu);
            namaIndividu = itemView.findViewById(R.id.namaIndividuTherapy);
            pekerjaanIndividu = itemView.findViewById(R.id.pekerjaanIndividuTherapy);
            lamaKerjaIndividu = itemView.findViewById(R.id.lama_kerja_individu);
            noTelpIndividu = itemView.findViewById(R.id.no_telp_therapy_individu);
            instagramIndividu = itemView.findViewById(R.id.instagram_therapy_individu);
            jumlahLikeIndividu = itemView.findViewById(R.id.jumlah_like_individu);
            jumlahUnlikeIndividu = itemView.findViewById(R.id.jumlah_unlike_individu);
            like = itemView.findViewById(R.id.imgLike_individu);
            unlike = itemView.findViewById(R.id.imgDislike_individu);
            imgAlamat = itemView.findViewById(R.id.imgAlamat);
        }
    }
}
