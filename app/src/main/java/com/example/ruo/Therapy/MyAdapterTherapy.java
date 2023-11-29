package com.example.ruo.Therapy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruo.R;

public class MyAdapterTherapy extends RecyclerView.Adapter<MyAdapterTherapy.ViewHolder> {

    Context context;
    public  MyListTherapyData[] therapyData;

    public MyAdapterTherapy(Context applicationContext, MyListTherapyData[] therapyData) {
        this.context = applicationContext;
        this.therapyData = therapyData;
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
        holder.imgPsikolog.setImageResource(therapyData[position].getImgPsikolog());
        holder.nama.setText(therapyData[position].getNama());
        holder.pekerjaan.setText(therapyData[position].getPekerjaan());
        holder.lama_kerja.setText(Integer.toString(therapyData[position].getLama_kerja()));
        holder.no_telp.setText(therapyData[position].getNo_telp());
        holder.instagram.setText(therapyData[position].getInstagram());
        holder.imgLike.setImageResource(therapyData[position].getImgLike());
        holder.imgDislike.setImageResource(therapyData[position].getImgDislike());
        holder.jumlahLike.setText(Integer.toString(therapyData[position].getJumlahLike()));
        holder.jumlahUnlike.setText(Integer.toString(therapyData[position].getJumlahUnlike()));
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
