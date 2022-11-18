package com.example.nana.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nana.databinding.HolderChatUserBinding;
import com.example.nana.interfaces.UserListener;
import com.example.nana.models.UserModel;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private final List<UserModel> users;
    private final UserListener userListener;

    public UsersAdapter(List<UserModel> users, UserListener userListener) {
        this.users = users;
        this.userListener = userListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HolderChatUserBinding holderChatUserBinding = HolderChatUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserViewHolder(holderChatUserBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserData(users.get(position));
//        holder.txtUsername.setText(users.get(position).getUsername());
//        Glide.with(context).load(users.get(position)
//                        .getProfilePic())
//                .error(R.drawable.ic_main_lumic)
//                .placeholder(R.drawable.ic_main_lumic)
//                .into(holder.img_pro);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        HolderChatUserBinding binding;

        public UserViewHolder(HolderChatUserBinding holderBinding) {
            super(holderBinding.getRoot());
            binding = holderBinding;
        }

        void setUserData(UserModel user) {
            binding.textUsername.setText(user.username);
            binding.textRecentMessage.setText(user.email);
//            binding.imageProfile.setImageBitmap(getUserImage(user.image));
            binding.getRoot().setOnClickListener(v -> userListener.onUserClicked(user));
        }
    }

    private Bitmap getUserImage(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
