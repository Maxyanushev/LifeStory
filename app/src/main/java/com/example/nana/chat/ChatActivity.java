package com.example.nana.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nana.R;
import com.example.nana.adapters.ViewPagerAdapterChats;
import com.example.nana.chat.fragments.GroupChatsFragment;
import com.example.nana.chat.fragments.PersonalChatsFragment;
import com.example.nana.databinding.ActivityChatBinding;
import com.example.nana.ui.profile.fragments.PostsFragment;
import com.example.nana.ui.profile.fragments.SavesFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewPagerAdapterChats adapter = new ViewPagerAdapterChats(this);
        binding.viewPager22.setAdapter(adapter);

        new TabLayoutMediator(binding.typeOfChats, binding.viewPager22, (tab, position) -> {
            if (position == 0) {
                tab.setText(R.string.personal);
                new PersonalChatsFragment();
            } else {
                tab.setText(R.string.groups);
                new GroupChatsFragment();
            }
        }).attach();
    }
}