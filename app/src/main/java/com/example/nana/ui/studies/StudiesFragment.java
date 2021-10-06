package com.example.nana.ui.studies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.nana.databinding.FragmentStudiesBinding;

public class StudiesFragment extends Fragment {

    private StudiesViewModel studiesViewModel;
    private FragmentStudiesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        studiesViewModel = new ViewModelProvider(this).get(StudiesViewModel.class);

        binding = FragmentStudiesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textStudies;
        studiesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
