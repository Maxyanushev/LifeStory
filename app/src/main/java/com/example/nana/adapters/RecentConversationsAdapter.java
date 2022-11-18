package com.example.nana.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nana.databinding.ItemContainerRecentConversionBinding;
import com.example.nana.interfaces.ConversionListener;
import com.example.nana.models.MessageModel;
import com.example.nana.models.UserModel;

import java.util.List;

public class RecentConversationsAdapter extends RecyclerView.Adapter<RecentConversationsAdapter.ConversionViewHolder> {

    private final List<MessageModel> messageModels;
    private final ConversionListener conversionListener;

    public RecentConversationsAdapter(List<MessageModel> messageModels, ConversionListener conversionListener) {
        this.messageModels = messageModels;
        this.conversionListener = conversionListener;
    }

    @NonNull
    @Override
    public ConversionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversionViewHolder(
                ItemContainerRecentConversionBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                ));
    }

    @Override
    public void onBindViewHolder(@NonNull ConversionViewHolder holder, int position) {
        holder.setData(messageModels.get(position));
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    class ConversionViewHolder extends RecyclerView.ViewHolder {

        ItemContainerRecentConversionBinding binding;

        ConversionViewHolder(ItemContainerRecentConversionBinding itemContainerRecentConversionBinding) {
            super(itemContainerRecentConversionBinding.getRoot());
            this.binding = itemContainerRecentConversionBinding;
        }

        public void setData(MessageModel messageModel) {
            binding.imageProfile.setImageBitmap(null);
//            binding.imageProfile.setImageBitmap(getConversionImage(messageModel.conversionImage));
            binding.textUsername.setText(messageModel.conversionName);
            binding.textRecentMessage.setText(messageModel.message);
            binding.getRoot().setOnClickListener(v -> {
                UserModel user = new UserModel();
                user.id = messageModel.conversionId;
                user.username = messageModel.conversionName;
                user.image = messageModel.conversionImage;
                conversionListener.onConversionClicked(user);
            });
        }
    }

//    private Bitmap getConversionImage(String encodedImage) {
//        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
//        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//    }
}
