package com.example.nana.ui.another;

import android.os.Bundle;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.nana.databinding.FragmentAnotherBinding;

public class AnotherFragment extends Fragment {

    private FragmentAnotherBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AnotherViewModel anotherViewModel = new ViewModelProvider(this).get(AnotherViewModel.class);

        binding = FragmentAnotherBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAnother;
        anotherViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
