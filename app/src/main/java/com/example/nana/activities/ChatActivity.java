package com.example.nana.activities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.nana.adapters.UsersAdapter;
import com.example.nana.core.BaseActivity;
import com.example.nana.databinding.ActivityChatBinding;
import com.example.nana.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ChatActivity extends BaseActivity {

    public ActivityChatBinding binding;

    private UsersAdapter usersAdapter;
    public UsersAdapter.OnUserClickListener onUserClickListener;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;

    private ArrayList<UserModel> users;
    public String myImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        initListeners();
    }

    public void init() {
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        users = new ArrayList<>();
        recyclerView = binding.recyclerView;
        swipeLayout = binding.swipeLayout;

        getUsers();
    }

    public void initListeners() {
        swipeLayout.setOnRefreshListener(() -> {
            getUsers();
            swipeLayout.setRefreshing(false);
        });

        onUserClickListener = position -> startActivity(new Intent(ChatActivity.this, MessagesActivity.class)
                .putExtra("username_of_roommate", users.get(position).getUsername())
                .putExtra("email_of_roommate", users.get(position).getEmail())
                .putExtra("image_of_roommate", users.get(position).getProfilePic()));
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
                    if (user.getEmail().equals(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail())) {
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