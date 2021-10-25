package com.example.nana.publication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.nana.databinding.FragmentPublicationBinding;

public class PublicationFragment extends Fragment {

    private FragmentPublicationBinding binding;
//    public ActivityMainBinding binding2;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        PublicationViewModel publicationViewModel = new ViewModelProvider(this).get(PublicationViewModel.class);

        binding = FragmentPublicationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPublication;
        publicationViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binding2.appBarMain.fab.setVisibility(View.VISIBLE);
        binding = null;
    }
}