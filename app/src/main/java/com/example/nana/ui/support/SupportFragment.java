package com.example.nana.ui.support;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.nana.core.BaseFragment;
import com.example.nana.databinding.FragmentSupportBinding;

public class SupportFragment extends BaseFragment {

    private FragmentSupportBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SupportViewModel supportViewModel = new ViewModelProvider(this).get(SupportViewModel.class);

        binding = FragmentSupportBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSupport;
        supportViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
