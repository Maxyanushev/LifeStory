package com.example.nana.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nana.databinding.HolderMessageBinding;
import com.example.nana.databinding.HolderReceivedMessageBinding;
import com.example.nana.models.MessageModel;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<MessageModel> messageModels;
    private Bitmap receiverProfileImage;
    private final String senderId;

    public static final int VIEW_TYPE_SEND = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;

    public void setReceiverProfileImage(Bitmap bitmap) {
        receiverProfileImage = bitmap;
    }

    public MessagesAdapter(List<MessageModel> messageModels, String senderId) {
        this.messageModels = messageModels;
        this.senderId = senderId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SEND) {
            return new SendMessageHolder(
                    HolderMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        } else {
            return new ReceivedMessageHolder(
                    HolderReceivedMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    ));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_SEND) {
            ((SendMessageHolder) holder).setData(messageModels.get(position));
        } else {
            ((ReceivedMessageHolder) holder).setData(messageModels.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messageModels.get(position).senderId.equals(senderId)) {
            return VIEW_TYPE_SEND;
        } else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    static class SendMessageHolder extends RecyclerView.ViewHolder {

        private final HolderMessageBinding binding;

        public SendMessageHolder(HolderMessageBinding holderMessageBinding) {
            super(holderMessageBinding.getRoot());
            binding = holderMessageBinding;
        }

        void setData(MessageModel messageModel) {
            binding.textHolderMessage.setText(messageModel.message);
            binding.textDataTime.setText(messageModel.dateTime);
        }
    }

    static class ReceivedMessageHolder extends RecyclerView.ViewHolder {

        private final HolderReceivedMessageBinding binding;

        ReceivedMessageHolder(HolderReceivedMessageBinding holderReceivedMessageBinding) {
            super(holderReceivedMessageBinding.getRoot());
            binding = holderReceivedMessageBinding;
        }

        void setData(MessageModel messageModel) {
            binding.textReceivedMessage.setText(messageModel.message);
            binding.textDataTime.setText(messageModel.dateTime);
//            if (receivedProfileImage != null) {
//                binding.imageProfile.setImageBitmap(receivedProfileImage);
//            }
        }
    }
}
