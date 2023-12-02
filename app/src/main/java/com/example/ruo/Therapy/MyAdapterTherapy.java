package com.example.ruo.Therapy;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruo.R;
import com.example.ruo.pojo.Therapy.AllTherapyItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapterTherapy extends RecyclerView.Adapter<MyAdapterTherapy.ViewHolder> {

    Context context;
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
    public void onBindViewHolder(@NonNull MyAdapterTherapy.ViewHolder holder, int position) {
        String fotoPsikolog = therapyData[position].getFotoPsikolog();
        int indexOfDot = fotoPsikolog.lastIndexOf(".");

        if (indexOfDot != -1) {
            // Jika titik (.) ditemukan dalam nama file, ambil substring sebelum titik
            String namaFileTanpaEkstensi = fotoPsikolog.substring(0, indexOfDot);

            int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(
                    namaFileTanpaEkstensi, "drawable", holder.itemView.getContext().getPackageName());

            Picasso.get().load(drawableResourceId).into(holder.imgPsikolog);

        } else {
            Log.d("Tag gagal", "onBindViewHolder: gagal");
        }

        holder.nama.setText(therapyData[position].getNamaPsikolog());
        holder.pekerjaan.setText(therapyData[position].getSpesialisPsikolog());
        holder.lama_kerja.setText(Integer.toString(therapyData[position].getLamaKarir()));
        holder.instagram.setText(therapyData[position].getMedsosPsikolog());
        holder.no_telp.setText(therapyData[position].getNoTelpPsikolog());
        holder.imgLike.setImageResource(therapyData[position].getImgLike());
        holder.imgDislike.setImageResource(therapyData[position].getImgDislike());
        holder.jumlahLike.setText(Integer.toString(therapyData[position].getLike()));
        holder.jumlahUnlike.setText(Integer.toString(therapyData[position].getDislike()));
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
        }
    }
}
