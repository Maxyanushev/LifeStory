package com.example.nana.ui.studies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.nana.databinding.FragmentStudiesBinding;

public class StudiesFragment extends Fragment {

    private FragmentStudiesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        StudiesViewModel studiesViewModel = new ViewModelProvider(this).get(StudiesViewModel.class);

        binding = FragmentStudiesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textStudies;
        studiesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
