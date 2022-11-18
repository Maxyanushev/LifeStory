package com.example.nana.activities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.nana.adapters.RecentConversationsAdapter;
import com.example.nana.core.BaseActivity;
import com.example.nana.databinding.ActivityChatBinding;
import com.example.nana.interfaces.ConversionListener;
import com.example.nana.models.MessageModel;
import com.example.nana.models.UserModel;
import com.example.nana.utilites.Constants;
import com.example.nana.utilites.PreferenceManager;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatActivity extends BaseActivity implements ConversionListener {

    private ActivityChatBinding binding;
    private PreferenceManager preferenceManager;

    private List<MessageModel> conversation;
    private RecentConversationsAdapter conversationsAdapter;
    private FirebaseFirestore database;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;

    private ArrayList<UserModel> users;
    public String myImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        initListeners();
        listenConversations();
    }

    public void init() {
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fabNewChat.setColorFilter(Color.argb(255, 255, 255, 255));

        preferenceManager = new PreferenceManager(getApplicationContext());
        conversation = new ArrayList<>();
        conversationsAdapter = new RecentConversationsAdapter(conversation, this);
        binding.conversationsRecyclerView.setAdapter(conversationsAdapter);
        database = FirebaseFirestore.getInstance();

        users = new ArrayList<>();
        swipeLayout = binding.swipeLayout;

        getToken();
        getUsers();
    }

    public void initListeners() {
        swipeLayout.setOnRefreshListener(() -> {
            getUsers();
            swipeLayout.setRefreshing(false);
        });

        binding.fabNewChat.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), UsersActivity.class));
        });

//        onUserClickListener = position -> startActivity(new Intent(ChatActivity.this, MessagesActivity.class)
//                .putExtra("username_of_roommate", users.get(position).getUsername())
//                .putExtra("email_of_roommate", users.get(position).getEmail())
//                .putExtra("image_of_roommate", users.get(position).getProfilePic()));
    }

    private void getUsers() {
//        users.clear();
//        FirebaseDatabase.getInstance().getReference("user").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    users.add(dataSnapshot.getValue(UserModel.class));
//                } usersAdapter = new UsersAdapter(users, ChatActivity.this, onUserClickListener);
//
//                recyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
//                recyclerView.setAdapter(usersAdapter);
//                recyclerView.setVisibility(View.VISIBLE);
//
//                for (UserModel user: users) {
//                    if (user.getEmail().equals(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail())) {
//                        myImageUrl = user.getProfilePic();
//                        return;
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    private void listenConversations() {
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
    }

    private final EventListener<QuerySnapshot> eventListener = ((value, error) -> {
        if (error != null) {
            return;
        }

        if (value != null) {
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    String receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    MessageModel messageModel = new MessageModel();
                    messageModel.senderId = senderId;
                    messageModel.receiverId = receiverId;

                    if (preferenceManager.getString(Constants.KEY_USER_ID).equals(senderId)) {
                        messageModel.conversionImage = documentChange.getDocument().getString(Constants.KEY_RECEIVER_IMAGE);
                        messageModel.conversionName = documentChange.getDocument().getString(Constants.KEY_RECEIVER_NAME);
                        messageModel.conversionId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    } else {
                        messageModel.conversionImage = documentChange.getDocument().getString(Constants.KEY_SENDER_IMAGE);
                        messageModel.conversionName = documentChange.getDocument().getString(Constants.KEY_SENDER_NAME);
                        messageModel.conversionId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    }

                    messageModel.message = documentChange.getDocument().getString(Constants.KEY_LAST_MESSAGE);
                    messageModel.dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                    conversation.add(messageModel);
                } else if (documentChange.getType() == DocumentChange.Type.MODIFIED) {
                    for (int i = 0; i < conversation.size(); i++) {
                        String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                        String receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                        if (conversation.get(i).senderId.equals(senderId) && conversation.get(i).receiverId.equals(receiverId)) {
                            conversation.get(i).message = documentChange.getDocument().getString(Constants.KEY_LAST_MESSAGE);
                            conversation.get(i).dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                            break;
                        }
                    }
                }
            }
            Collections.sort(conversation, (obj1, obj2) -> obj2.dateObject.compareTo(obj1.dateObject));
            conversationsAdapter.notifyDataSetChanged();
            binding.conversationsRecyclerView.smoothScrollToPosition(0);
            binding.conversationsRecyclerView.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
        }
    });

    private void getToken() {
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }

    private void updateToken(String token) {
        preferenceManager.putString(Constants.KEY_FCM_TOKEN, token);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference = database.collection(Constants.KEY_COLLECTION_USERS)
                .document(preferenceManager.getString(Constants.KEY_USER_ID));
        documentReference.update(Constants.KEY_FCM_TOKEN, token)
                .addOnFailureListener(e -> showToast("Хрень какая-то с токеном"));
    }

    public void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConversionClicked(UserModel userModel) {
        Intent intent = new Intent(getApplicationContext(), MessagesActivity.class);
        intent.putExtra(Constants.KEY_USER, userModel);
        startActivity(intent);
    }
}