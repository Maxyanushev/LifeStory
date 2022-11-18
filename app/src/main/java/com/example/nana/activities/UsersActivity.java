package com.example.nana.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.nana.adapters.UsersAdapter;
import com.example.nana.core.BaseActivity;
import com.example.nana.databinding.ActivityUsersBinding;
import com.example.nana.interfaces.UserListener;
import com.example.nana.models.UserModel;
import com.example.nana.utilites.Constants;
import com.example.nana.utilites.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends BaseActivity implements UserListener {

    private ActivityUsersBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        initListeners();
        getUsers();
    }

    private void init() {
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(getApplicationContext());
    }

    private void initListeners() {
        binding.imageButtonBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void getUsers() {
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .get()
                .addOnCompleteListener(task -> {
                   loading(false);
                   String currentUserId = preferenceManager.getString(Constants.KEY_USER_ID);
                   if (task.isSuccessful() && task.getResult() != null) {
                       List<UserModel> users = new ArrayList<>();
                       for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                           if (currentUserId.equals(queryDocumentSnapshot.getId())) {
                               continue;
                           }
                           UserModel userModel = new UserModel();
                           userModel.username = queryDocumentSnapshot.getString(Constants.KEY_NAME);
                           userModel.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                           userModel.image = queryDocumentSnapshot.getString(Constants.KEY_IMAGE);
                           userModel.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                           userModel.id = queryDocumentSnapshot.getId();
                           users.add(userModel);
                       }
                       if (users.size() > 0) {
                           UsersAdapter usersAdapter = new UsersAdapter(users, this);
                           binding.usersRecyclerView.setAdapter(usersAdapter);
                           binding.usersRecyclerView.setVisibility(View.VISIBLE);
                       } else {
                           showErrorMessage();
                       }
                   } else {
                       showErrorMessage();
                   }
                });
    }

    private void showErrorMessage() {
        binding.textErrorMessage.setText(String.format("%s", "No user available"));
        binding.textErrorMessage.setVisibility(View.VISIBLE);
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onUserClicked(UserModel user) {
        Intent intent = new Intent(getApplicationContext(), MessagesActivity.class);
        intent.putExtra(Constants.KEY_USER, user);
        startActivity(intent);
        finish();
    }
}