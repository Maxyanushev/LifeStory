package com.example.nana.publication.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nana.R;
import com.example.nana.core.BaseFragment;
import com.example.nana.databinding.FragmentPublicationBinding;

public class PublicationFragment extends BaseFragment {

    private FragmentPublicationBinding binding;

    private final PreviewPublicationFragment previewPublicationFragment = new PreviewPublicationFragment();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPublicationBinding.inflate(inflater, container, false);

        init();
        initListeners();

        return binding.getRoot();
    }

    private void init() {

    }

    private void initListeners() {
        binding.cardButtonNext.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, previewPublicationFragment);
            fragmentTransaction.commit();
        });
    }
}