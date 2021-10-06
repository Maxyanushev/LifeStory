package com.example.nana.ui.rave;

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

import com.example.nana.databinding.FragmentRaveBinding;

public class RaveFragment extends Fragment {
    private RaveViewModel raveViewModel;
    private FragmentRaveBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        raveViewModel = new ViewModelProvider(this).get(RaveViewModel.class);

        binding = FragmentRaveBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textRave;
        raveViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
