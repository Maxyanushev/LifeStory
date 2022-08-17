package com.example.nana.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.nana.R;
import com.example.nana.adapters.ViewPagerAdapter;
import com.example.nana.core.BaseFragment;
import com.example.nana.databinding.FragmentProfileBinding;
import com.example.nana.ui.profile.fragments.PostsFragment;
import com.example.nana.ui.profile.fragments.SavesFragment;
import com.google.android.material.tabs.TabLayoutMediator;

public class ProfileFragment extends BaseFragment {

    private FragmentProfileBinding binding;
    public ProfileViewModel profileViewModel;
    public View root;

    public TextView textView;

    public static boolean toolbar = false;

    public ProfileFragment(boolean toolbarVisibility) {
        toolbar = toolbarVisibility;
    }

    public ProfileFragment() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        textView = binding.textProfile;
        profileViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        root = binding.getRoot();

        binding.viewPager2.setAdapter(new ViewPagerAdapter(this));
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

        binding.backButton.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (toolbar) {
            binding.cardToolbarProfile.setVisibility(View.GONE);
        } else {
            binding.cardToolbarProfile.setVisibility(View.VISIBLE);
        }
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