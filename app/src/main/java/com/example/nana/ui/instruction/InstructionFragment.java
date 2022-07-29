package com.example.nana.ui.instruction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.nana.core.BaseFragment;
import com.example.nana.databinding.FragmentInstructionBinding;

public class InstructionFragment extends BaseFragment {

    private FragmentInstructionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        InstructionViewModel instructionViewModel = new ViewModelProvider(this).get(InstructionViewModel.class);

        binding = FragmentInstructionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textInstruction;
        instructionViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
