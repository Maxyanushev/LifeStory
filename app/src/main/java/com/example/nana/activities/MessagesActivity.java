package com.example.nana.activities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nana.R;
import com.example.nana.adapters.MessagesAdapter;
import com.example.nana.core.BaseActivity;
import com.example.nana.databinding.ActivityMessagesBinding;
import com.example.nana.models.MessageModel;
import com.example.nana.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MessagesActivity extends BaseActivity {

    public ActivityMessagesBinding binding;
    private MessagesAdapter massageAdapter;

    private RecyclerView recyclerView;
    private EditText editMassageInput;
    public TextView txtMassageWith;
    public ImageView imgToolbar, imgSend;

    private final ArrayList<MessageModel> messages = new ArrayList<>();

    private String usernameOfTheRoommate, emailOfRoommate, chatRoomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        initListeners();
    }

    public void init() {
        binding = ActivityMessagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        usernameOfTheRoommate = getIntent().getStringExtra("username_of_roommate");
        emailOfRoommate = getIntent().getStringExtra("email_of_roommate");

        recyclerView = binding.recyclerMessages;
        imgSend = binding.imgSend;
        editMassageInput = binding.editMessage;
        txtMassageWith = binding.txtChattingWith;
        imgToolbar = binding.imgToolbar;

        txtMassageWith.setText(usernameOfTheRoommate);

        massageAdapter = new MessagesAdapter(messages, getIntent().getStringExtra("my_img"), getIntent().getStringExtra("img_of_roommate"), MessagesActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(massageAdapter);
        Glide.with(MessagesActivity.this).load(getIntent().getStringExtra("img_of_roommate")).placeholder(R.drawable.test_img).error(R.drawable.test_img).into(imgToolbar);

        setUpChatRoom();
    }

    public void initListeners() {
        imgSend.setOnClickListener(v -> {
            FirebaseDatabase.getInstance().getReference("messages/" + chatRoomId).push().setValue(new MessageModel(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail(), emailOfRoommate, editMassageInput.getText().toString()));
            editMassageInput.setText("");
        });
    }

    public void setUpChatRoom() {
        FirebaseDatabase.getInstance().getReference("user/" + FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String myUserName = Objects.requireNonNull(snapshot.getValue(UserModel.class)).getUsername();
                        if (usernameOfTheRoommate.compareTo(myUserName) > 0) {
                            chatRoomId = myUserName + usernameOfTheRoommate;
                        } else if (usernameOfTheRoommate.compareTo(myUserName) == 0) {
                            chatRoomId = myUserName + usernameOfTheRoommate;
                        } else {
                            chatRoomId = usernameOfTheRoommate + myUserName;
                        }
                        attachMassageListener(chatRoomId);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void attachMassageListener(String chatRoomId) {
        FirebaseDatabase.getInstance().getReference("messages/" + chatRoomId).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    messages.add(dataSnapshot.getValue(MessageModel.class));
                }
                massageAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(messages.size() - 1);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}