package com.example.ruo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruo.API.APIProfile;
import com.example.ruo.pojo.Profile.DataCommentItem;
import com.example.ruo.pojo.Profile.DataMyMessageItem;

import java.util.ArrayList;

public class KomenUtasAdapter extends RecyclerView.Adapter<KomenUtasAdapter.ViewHolder> {

    APIProfile apiProfile;
    Context context;
    public DataCommentItem[] komenUtas;

    public KomenUtasAdapter(Context applicationContext, ArrayList<DataCommentItem> komenUtas) {
        this.context = applicationContext;
        this.komenUtas = komenUtas.toArray(new DataCommentItem[0]);
    }

    @NonNull
    @Override
    public KomenUtasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_komen_utasku, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull KomenUtasAdapter.ViewHolder holder, int position) {
        holder.komenutas.setText(komenUtas[position].getIsiComment());
    }

    @Override
    public int getItemCount() {
        return komenUtas.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView komenutas;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            komenutas = itemView.findViewById(R.id.komenutasku);
        }
    }
}
