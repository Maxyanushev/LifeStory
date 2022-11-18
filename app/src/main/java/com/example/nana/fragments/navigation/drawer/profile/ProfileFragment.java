package com.example.nana.fragments.navigation.drawer.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.nana.R;
import com.example.nana.activities.LoginActivity;
import com.example.nana.adapters.ViewPagerAdapterProfile;
import com.example.nana.core.BaseFragment;
import com.example.nana.databinding.FragmentProfileBinding;
import com.example.nana.fragments.navigation.drawer.profile.fragments.PostsFragment;
import com.example.nana.fragments.navigation.drawer.profile.fragments.SavesFragment;
import com.example.nana.utilites.Constants;
import com.example.nana.utilites.PreferenceManager;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ProfileFragment extends BaseFragment {

    PreferenceManager preferenceManager;

    private FragmentProfileBinding binding;
    public ProfileViewModel profileViewModel;
    public View root;

    public TextView textView;

    public static boolean toolbar = true;

    public ProfileFragment(boolean toolbarVisibility) {
        toolbar = toolbarVisibility;
    }

    public ProfileFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        preferenceManager = new PreferenceManager(context);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        textView = binding.textProfile;
        profileViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        root = binding.getRoot();

        binding.viewPager2.setAdapter(new ViewPagerAdapterProfile(this));
        binding.tabLayout.setTabIconTint(null);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager2,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText(R.string.posts);
                        new PostsFragment();
                    } else {
                        tab.setText(R.string.saved);
                        new SavesFragment();
                    }
                }).attach();

        binding.backButton.setOnClickListener(v -> requireActivity().onBackPressed());
        binding.buttonExit.setOnClickListener(v -> {
            FirebaseFirestore database = FirebaseFirestore.getInstance();
            DocumentReference documentReference = database.collection(Constants.KEY_COLLECTION_USERS)
                    .document(preferenceManager.getString(Constants.KEY_USER_ID));
            HashMap<String, Object> updates = new HashMap<>();
            updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());
            documentReference.update(updates).addOnSuccessListener(unused -> {
                preferenceManager.clear();
                startActivity(new Intent(requireActivity().getApplicationContext(), LoginActivity.class));
            }).addOnFailureListener(e -> Toast.makeText(requireActivity().getApplicationContext(), "Не получаеться!", Toast.LENGTH_SHORT));
        });

        binding.layoutEditProfile.setOnClickListener(v -> {

        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!toolbar) {
            binding.cardToolbarProfile.setVisibility(View.GONE);
        } else {
            binding.cardToolbarProfile.setVisibility(View.VISIBLE);
        }
        toolbar = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}