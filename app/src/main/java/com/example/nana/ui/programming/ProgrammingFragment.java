package com.example.nana.ui.programming;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.nana.databinding.FragmentProgrammingBinding;

public class ProgrammingFragment extends Fragment {

    private FragmentProgrammingBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ProgrammingViewModel programmingViewModel = new ViewModelProvider(this).get(ProgrammingViewModel.class);

        binding = FragmentProgrammingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textProgramming;
        programmingViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
