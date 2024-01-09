package com.example.ruo.message;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ruo.R;
import com.example.ruo.pojo.message.MessageResponse;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<MessageResponse> messagesList;

    private Context context;

    public MessageAdapter(Context context, List<MessageResponse> messagesList) {
        this.context = context;
        this.messagesList = messagesList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        MessageResponse message = messagesList.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvTimestamp, tvMessage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            tvMessage = itemView.findViewById(R.id.tvMessage);

        }

        public void bind(MessageResponse message) {
            if (message.getIdHide() == 1) {
                SharedPreferences sharedPref = itemView.getContext().getSharedPreferences("env", Context.MODE_PRIVATE);
                int userId = sharedPref.getInt("id_user", 0);
                if (message.getIdUser() == userId) {
                    tvName.setText(String.valueOf(message.getIdUser()));
                } else {
                    tvName.setText("xxxxxxxx");
                }
            } else {
                tvName.setText(String.valueOf(message.getIdUser()));
            }
            ConvertDate convertDate = new ConvertDate();
            tvTimestamp.setText(convertDate.convertDate(message.getCreatedAt()));
            tvMessage.setText(message.getIsiMessage());
            tvName.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), DetailProfileMessageActivity.class);
                intent.putExtra("messageId", message.getIdMessage());
                itemView.getContext().startActivity(intent);
            });
            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), DetailContentMessageActivity.class);
                intent.putExtra("idMessage", message.getIdMessage());
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
