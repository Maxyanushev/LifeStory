package com.example.nana.fragments.navigation.drawer.instruction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.nana.core.BaseFragment;
import com.example.nana.databinding.FragmentInstructionBinding;

public class InstructionFragment extends BaseFragment {

    private FragmentInstructionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInstructionBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
