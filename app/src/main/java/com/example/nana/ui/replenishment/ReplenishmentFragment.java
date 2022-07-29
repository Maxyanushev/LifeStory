package com.example.nana.ui.replenishment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.example.nana.core.BaseFragment;
import com.example.nana.databinding.FragmentReplenishmentBinding;

public class ReplenishmentFragment extends BaseFragment {

    private FragmentReplenishmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ReplenishmentViewModel replenishmentViewModel = new ViewModelProvider(this).get(ReplenishmentViewModel.class);

        binding = FragmentReplenishmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textReplenishment;
        replenishmentViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
