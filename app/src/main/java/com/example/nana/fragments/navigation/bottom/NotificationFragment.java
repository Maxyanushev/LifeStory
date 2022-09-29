package com.example.nana.fragments.navigation.bottom;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nana.R;
import com.example.nana.activities.ChatActivity;
import com.example.nana.adapters.NotificationAdapter;
import com.example.nana.databinding.FragmentNotificationBinding;

public class NotificationFragment extends Fragment {

    FragmentNotificationBinding binding;

    NotificationAdapter notificationAdapter;

    RecyclerView recyclerView;

    public NotificationFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationBinding.inflate(inflater, container, false);

        init();
        initListeners();

        return binding.getRoot();
    }

    public void init() {
        recyclerView = binding.recyclerNotification;
        notificationAdapter = new NotificationAdapter(requireActivity());

        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setAdapter(notificationAdapter);
    }

    public void initListeners() {

    }
}