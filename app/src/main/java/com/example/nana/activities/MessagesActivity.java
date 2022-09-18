package com.example.nana.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.nana.models.MessageModel;
import com.example.nana.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MessagesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText editMassageInput;
    TextView txtMassageWith;

    ArrayList<MessageModel> massages;
    ImageView imgToolbar, imgSend;

    String usernameOfTheRoommate, emailOfRoommate, chatRoomId;

    MessagesAdapter massageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        usernameOfTheRoommate = getIntent().getStringExtra("username_of_roommate");
        emailOfRoommate = getIntent().getStringExtra("email_of_roommate");

        recyclerView = findViewById(R.id.recyclerMessages);
        imgSend = findViewById(R.id.imageView2);
        editMassageInput = findViewById(R.id.editMassage);
        txtMassageWith = findViewById(R.id.txtChattingWith);
        imgToolbar = findViewById(R.id.imgToolbar);
        txtMassageWith.setText(usernameOfTheRoommate);

        massages = new ArrayList<>();

        imgSend.setOnClickListener(v -> {
            FirebaseDatabase.getInstance().getReference("massages/" + chatRoomId).push().setValue(new MessageModel(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail(), emailOfRoommate, editMassageInput.getText().toString()));
            editMassageInput.setText("");
        });

        massageAdapter = new MessagesAdapter(massages, getIntent().getStringExtra("my_img"), getIntent().getStringExtra("img_of_roommate"), MessagesActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(massageAdapter);
        Glide.with(MessagesActivity.this).load(getIntent().getStringExtra("img_of_roommate")).placeholder(R.drawable.test_img).error(R.drawable.test_img).into(imgToolbar);

        setUpChatRoom();
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
        FirebaseDatabase.getInstance().getReference("massages/" + chatRoomId).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                massages.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    massages.add(dataSnapshot.getValue(MessageModel.class));
                }
                massageAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(massages.size() - 1);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}