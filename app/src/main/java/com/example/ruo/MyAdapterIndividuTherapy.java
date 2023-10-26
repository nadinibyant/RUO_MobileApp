package com.example.ruo;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class MyAdapterIndividuTherapy extends RecyclerView.Adapter<MyAdapterIndividuTherapy.ViewHolder> {

    Context context;

    public MyListIndividuTherapyData[] myListIndividuTherapyData;

    public MyAdapterIndividuTherapy(Context context, MyListIndividuTherapyData[] myListIndividuTherapyData){
        this.context = context;
        this.myListIndividuTherapyData = myListIndividuTherapyData;
    }

    @NonNull
    @Override
    public MyAdapterIndividuTherapy.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_single_list_individu_therapy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterIndividuTherapy.ViewHolder holder, int position) {
        holder.imgPsikologIndividu.setImageResource(myListIndividuTherapyData[position].getImgPsikolog());
        holder.editTherapyIndividu.setTag(Integer.toString(myListIndividuTherapyData[position].getIdTherapy()));
        holder.editTherapyIndividu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String therapyId = (String) view.getTag();
                System.out.println("tag : " + therapyId);

                Intent intent = new Intent(context, TherapyActivityEdit.class);
                intent.putExtra("therapyId", therapyId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.deleteTherapyIndividu.setTag(myListIndividuTherapyData[position].getIdTherapy());
        holder.namaIndividu.setText(myListIndividuTherapyData[position].getNama());
        holder.pekerjaanIndividu.setText(myListIndividuTherapyData[position].getPekerjaan());
        holder.lamaKerjaIndividu.setText(Integer.toString(myListIndividuTherapyData[position].getLama_kerja()));
        holder.noTelpIndividu.setText(myListIndividuTherapyData[position].getNo_telp());
        holder.instagramIndividu.setText(myListIndividuTherapyData[position].getInstagram());
        holder.jumlahLikeIndividu.setText(Integer.toString(myListIndividuTherapyData[position].getJumlahLike()));
        holder.jumlahUnlikeIndividu.setText(Integer.toString(myListIndividuTherapyData[position].getJumlahUnlike()));
        holder.editTherapyIndividu.setImageResource(myListIndividuTherapyData[position].getIdImgEdit());
        holder.deleteTherapyIndividu.setImageResource(myListIndividuTherapyData[position].getImgDel());
        holder.like.setImageResource(myListIndividuTherapyData[position].getImgLike());
        holder.unlike.setImageResource(myListIndividuTherapyData[position].getImgDislike());

    }

    @Override
    public int getItemCount() {
        return myListIndividuTherapyData.length;
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
