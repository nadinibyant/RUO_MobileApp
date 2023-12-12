package com.example.ruo.Therapy;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruo.R;
import com.example.ruo.pojo.Therapy.MyTherapyItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapterIndividuTherapy extends RecyclerView.Adapter<MyAdapterIndividuTherapy.ViewHolder> {

    Context context;

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
            String baseUrl = "http://10.0.2.2:3000";
            String photoUrl = baseUrl + "/fotoPsikolog/" + fotoPsikolog;
            Log.d("TAG url foto", "onBindViewHolder: " + photoUrl);
            Picasso.get().load(photoUrl).into(holder.imgPsikologIndividu);
        }
        holder.editTherapyIndividu.setTag(myTherapyItems[position].getIdTherapy());
        holder.editTherapyIndividu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String therapyId = (String) view.getTag();
                Intent intent = new Intent(context, TherapyActivityEdit.class);
                intent.putExtra("therapyId", therapyId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
        holder.deleteTherapyIndividu.setTag(myTherapyItems[position].getIdTherapy());
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
    }

    @Override
    public int getItemCount() {
        return myTherapyItems.length;
    }

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
        }
    }
}
