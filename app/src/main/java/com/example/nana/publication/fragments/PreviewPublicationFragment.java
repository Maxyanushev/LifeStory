package com.example.nana.publication.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nana.core.BaseFragment;
import com.example.nana.databinding.FragmentPreviewPublicationBinding;

public class PreviewPublicationFragment extends BaseFragment {

    public FragmentPreviewPublicationBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPreviewPublicationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}