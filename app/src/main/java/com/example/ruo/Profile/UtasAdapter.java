package com.example.ruo.Profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.camera2.CaptureRequest;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruo.API.APIProfile;
import com.example.ruo.API.APITherapy;
import com.example.ruo.APIClient;
import com.example.ruo.LoginActivity;
import com.example.ruo.Therapy.TherapyActivityEdit;
import com.example.ruo.pojo.Profile.DataMyMessageItem;
import com.example.ruo.R;
import com.example.ruo.pojo.Profile.DeleteMyMessageResponse;
import com.example.ruo.pojo.Therapy.AllTherapyItem;
import com.example.ruo.pojo.Therapy.DeleteTherapyResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UtasAdapter extends RecyclerView.Adapter<UtasAdapter.ViewHolder> {
//    private ArrayList<UtasModel> utasModel;

//    ===============
//    private ArrayList<DataMyMessageItem> utasModel;
////    public UtasAdapter(ArrayList<UtasModel> utasModel) {
////        this.utasModel = utasModel;
////    }
//    public UtasAdapter(ArrayList<DataMyMessageItem> utasModel) {
//        this.utasModel = utasModel;
//    }
//    ==================

    APIProfile apiProfile;
    public DataMyMessageItem[] MessageData;

    Context context;
    public UtasAdapter(Context applicationContext, ArrayList<DataMyMessageItem> MessageData) {
        this.context = applicationContext;
        this.MessageData = MessageData.toArray(new DataMyMessageItem[0]);
    }

    @NonNull
    @Override
    public UtasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_utas, parent, false );
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull UtasAdapter.ViewHolder holder, int position) {

//        holder.cardView.setTag(MessageData[position].getIdMessage());
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int utasId = (int) view.getTag();
//                Intent intent = new Intent(holder.itemView.getContext(), KomenUtasActivity.class);
//                intent.putExtra("utasId", utasId);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    int utasId = MessageData[adapterPosition].getIdMessage();
                    String utasMessage = MessageData[adapterPosition].getIsiMessage();

                    Intent intent = new Intent(holder.itemView.getContext(), KomenUtasActivity.class);
                    intent.putExtra("utasId", utasId);
                    intent.putExtra("utasMessage", utasMessage);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });


        holder.editUtas.setTag(MessageData[position].getIdMessage());
        holder.editUtas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int utasId = (int) view.getTag();
                Intent intent = new Intent(holder.itemView.getContext(), EditUtasActivity.class);
                intent.putExtra("utasId", utasId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


        holder.hapusUtas.setTag(MessageData[position].getIdMessage());
        holder.hapusUtas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int utasId = (int) view.getTag();
//                Log.d("DeleteButton", "Clicked delete button for utasId: " + utasId);
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle("Konfirmasi Hapus");
                builder.setMessage("Apakah Anda yakin ingin menghapus utas ini?");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        apiProfile = APIClient.getClient().create(APIProfile.class);
                        SharedPreferences sharedPref = context.getSharedPreferences("env", Context.MODE_PRIVATE);
                        String authToken = "Bearer " + sharedPref.getString("ACCESS_TOKEN_SECRET", null);
                        if (authToken != null) {
                            Call<DeleteMyMessageResponse> call = apiProfile.delMessageResp(authToken, utasId);
                            call.enqueue(new Callback<DeleteMyMessageResponse>() {
                                @Override
                                public void onResponse(Call<DeleteMyMessageResponse> call, Response<DeleteMyMessageResponse> response) {
                                    if (response.isSuccessful()){
                                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onFailure(Call<DeleteMyMessageResponse> call, Throwable t) {
                                }

                            });
                        } else {
                            Intent intent = new Intent(context, LoginActivity.class);
                            context.startActivity(intent);
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        holder.iniutas.setText(MessageData[position].getIsiMessage());


    }

    @Override
    public int getItemCount() {
        return MessageData.length;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView editUtas;
        ImageView hapusUtas;
        TextView iniutas;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iniutas = itemView.findViewById(R.id.utasku);
            editUtas= itemView.findViewById(R.id.editUtas);
            hapusUtas = itemView.findViewById(R.id.inikitahapusUtas);
            cardView = itemView.findViewById(R.id.cardutas);
        }
    }
}
