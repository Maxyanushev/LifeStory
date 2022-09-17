package com.example.nana.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.nana.MessagesActivity;
import com.example.nana.R;
import com.example.nana.adapters.UsersAdapter;
import com.example.nana.databinding.ActivityChatBinding;
import com.example.nana.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;

    private RecyclerView recyclerView;
    private ArrayList<UserModel> users;
    private UsersAdapter usersAdapter;
    UsersAdapter.OnUserClickListener onUserClickListener;
    private SwipeRefreshLayout swipeLayout;
    String myImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        users = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        swipeLayout = findViewById(R.id.swipeLayout);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUsers();
                swipeLayout.setRefreshing(false);
            }
        });

        onUserClickListener = new UsersAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(int position) {
                startActivity(new Intent(ChatActivity.this, MessagesActivity.class)
                        .putExtra("username_of_roommate", users.get(position).getUsername())
                        .putExtra("email_of_roommate", users.get(position).getEmail())
                        .putExtra("image_of_roommate", users.get(position).getProfilePic()));
            }
        };

        getUsers();
    }

    private void getUsers() {
        users.clear();
        FirebaseDatabase.getInstance().getReference("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    users.add(dataSnapshot.getValue(UserModel.class));
                } usersAdapter = new UsersAdapter(users, ChatActivity.this, onUserClickListener);

                recyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
                recyclerView.setAdapter(usersAdapter);
                recyclerView.setVisibility(View.VISIBLE);

                for (UserModel user: users) {
                    if (user.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                        myImageUrl = user.getProfilePic();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}