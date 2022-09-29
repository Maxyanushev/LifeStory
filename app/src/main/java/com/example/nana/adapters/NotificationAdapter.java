package com.example.nana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nana.R;
import com.example.nana.models.UserModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder>{

    Context context;

    public NotificationAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        return new NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class NotificationHolder extends RecyclerView.ViewHolder {

        TextView textData, textName, textMessage, textClockTime;
        CircleImageView imageView;
        ImageButton btnOptions;
        View viewLine;

        public NotificationHolder(@NonNull View itemView) {
            super(itemView);

            textData = itemView.findViewById(R.id.textData);
            textName = itemView.findViewById(R.id.textName);
            textMessage = itemView.findViewById(R.id.textMessage);
            textClockTime = itemView.findViewById(R.id.textClockTime);

            imageView = itemView.findViewById(R.id.circleImageView);

            btnOptions = itemView.findViewById(R.id.btnOptions);
            viewLine = itemView.findViewById(R.id.viewLine);
        }
    }
}
