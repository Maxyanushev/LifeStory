package com.example.nana.fragments.navigation.drawer.replenishment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nana.core.BaseFragment;
import com.example.nana.databinding.FragmentReplenishmentBinding;

public class ReplenishmentFragment extends BaseFragment {

    private FragmentReplenishmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReplenishmentBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
