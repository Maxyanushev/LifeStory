package com.example.nana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nana.R;
import com.example.nana.models.UserModel;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserHolder> {

    private final ArrayList<UserModel> users;
    private final Context context;
    private final OnUserClickListener onUserClickListener;

    public UsersAdapter(ArrayList<UserModel> users, Context context, OnUserClickListener onUserClickListener) {
        this.users = users;
        this.context = context;
        this.onUserClickListener = onUserClickListener;
    }

    public interface OnUserClickListener {
        void onUserClick(int position);
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_chat_user, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        holder.txtUsername.setText(users.get(position).getUsername());
        Glide.with(context).load(users.get(position)
                        .getProfilePic())
                .error(R.drawable.ic_main_lumic)
                .placeholder(R.drawable.ic_main_lumic)
                .into(holder.img_pro);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserHolder extends RecyclerView.ViewHolder {

        TextView txtUsername;
        ImageView img_pro;

        public UserHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(v -> onUserClickListener.onUserClick(getAdapterPosition()));

            txtUsername = itemView.findViewById(R.id.txtUsername);
            img_pro = itemView.findViewById(R.id.imgPro);
        }
    }
}
