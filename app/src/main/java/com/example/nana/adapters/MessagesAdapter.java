package com.example.nana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nana.R;
import com.example.nana.models.MessageModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Objects;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageHolder> {

    ArrayList<MessageModel> messages;
    private final String senderImg, receiverImg;
    private final Context context;

    public MessagesAdapter(ArrayList<MessageModel> messages, String senderImg, String receiverImg, Context context) {
        this.messages = messages;
        this.senderImg = senderImg;
        this.receiverImg = receiverImg;
        this.context = context;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_message, parent, false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        holder.txtMessage.setText(messages.get(position).getContent());
        ConstraintLayout constraintLayout = holder.ccLayout;

        if (messages.get(position).getSender().equals(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail())) {
            Glide.with(context).load(senderImg).error(R.drawable.test_img).placeholder(R.drawable.test_img).into(holder.profImage);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            holder.txtMessage.setBackgroundResource(R.drawable.round_corners_receiver);
            constraintSet.clear(R.id.small_profile_picture, ConstraintSet.LEFT);
            constraintSet.clear(R.id.txtMassage, ConstraintSet.LEFT);
            constraintSet.connect(R.id.small_profile_picture, ConstraintSet.RIGHT, R.id.ccLayout, ConstraintSet.RIGHT, 16);
            constraintSet.connect(R.id.txtMassage, ConstraintSet.RIGHT, R.id.small_profile_picture, ConstraintSet.LEFT, 16);
            constraintSet.applyTo(constraintLayout);
        } else {
            Glide.with(context).load(receiverImg).error(R.drawable.test_img).placeholder(R.drawable.test_img).into(holder.profImage);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            holder.txtMessage.setBackgroundResource(R.drawable.round_corners_sender);
            constraintSet.clear(R.id.small_profile_picture, ConstraintSet.RIGHT);
            constraintSet.clear(R.id.txtMassage, ConstraintSet.RIGHT);
            constraintSet.connect(R.id.small_profile_picture, ConstraintSet.LEFT, R.id.ccLayout, ConstraintSet.LEFT, 16);
            constraintSet.connect(R.id.txtMassage, ConstraintSet.LEFT, R.id.small_profile_picture, ConstraintSet.RIGHT, 16);
            constraintSet.applyTo(constraintLayout);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessageHolder extends RecyclerView.ViewHolder {

        ConstraintLayout ccLayout;
        ImageView profImage;
        TextView txtMessage;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);

            ccLayout = itemView.findViewById(R.id.ccLayout);
            profImage = itemView.findViewById(R.id.small_profile_picture);
            txtMessage = itemView.findViewById(R.id.txtMassage);
        }
    }
}
