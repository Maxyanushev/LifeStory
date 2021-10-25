package com.example.nana.ui.rave;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.nana.databinding.FragmentRaveBinding;

public class RaveFragment extends Fragment {

    private FragmentRaveBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RaveViewModel raveViewModel = new ViewModelProvider(this).get(RaveViewModel.class);

        binding = FragmentRaveBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textRave;
        raveViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
