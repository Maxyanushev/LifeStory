package com.example.nana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nana.R;
import com.example.nana.models.MessageModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageHolder> {

    ArrayList<MessageModel> massages;
    private String senderImg, receiverImg;
    private Context context;

    public MessagesAdapter(ArrayList<MessageModel> massages, String senderImg, String receiverImg, Context context) {
        this.massages = massages;
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
        holder.txtMassage.setText(massages.get(position).getContent());
        ConstraintLayout constraintLayout = holder.ccLayout;

        if (massages.get(position).getSender().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
            Glide.with(context).load(senderImg).error(R.drawable.ic_main_lumic).placeholder(R.drawable.ic_main_lumic).into(holder.profImage);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            constraintSet.clear(R.id.profile_cardview, ConstraintSet.LEFT);
            constraintSet.clear(R.id.txtMassage, ConstraintSet.LEFT);
            constraintSet.connect(R.id.profile_cardview, ConstraintSet.RIGHT, R.id.ccLayout, ConstraintSet.RIGHT, 16);
            constraintSet.connect(R.id.txtMassage, ConstraintSet.RIGHT, R.id.profile_cardview, ConstraintSet.LEFT, 16);
            constraintSet.applyTo(constraintLayout);
        } else {
            Glide.with(context).load(receiverImg).error(R.drawable.ic_main_lumic).placeholder(R.drawable.ic_main_lumic).into(holder.profImage);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            constraintSet.clear(R.id.profile_cardview, ConstraintSet.RIGHT);
            constraintSet.clear(R.id.txtMassage, ConstraintSet.RIGHT);
            constraintSet.connect(R.id.profile_cardview, ConstraintSet.LEFT, R.id.ccLayout, ConstraintSet.LEFT, 16);
            constraintSet.connect(R.id.txtMassage, ConstraintSet.LEFT, R.id.profile_cardview, ConstraintSet.RIGHT, 16);
            constraintSet.applyTo(constraintLayout);
        }
    }

    @Override
    public int getItemCount() {
        return massages.size();
    }

    class MessageHolder extends RecyclerView.ViewHolder {

        ConstraintLayout ccLayout;
        CardView profile_cardview;
        ImageView profImage;
        TextView txtMassage;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);

            ccLayout = itemView.findViewById(R.id.ccLayout);
            profile_cardview = itemView.findViewById(R.id.profile_cardview);
            profImage = itemView.findViewById(R.id.small_profile_picture);
            txtMassage = itemView.findViewById(R.id.txtMassage);
        }
    }
}
