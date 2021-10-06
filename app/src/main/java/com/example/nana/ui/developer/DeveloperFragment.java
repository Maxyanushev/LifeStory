package com.example.nana.ui.developer;

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

import com.example.nana.databinding.FragmentDeveloperBinding;

public class DeveloperFragment extends Fragment {

    private DeveloperViewModel developerViewModel;
    private FragmentDeveloperBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        developerViewModel = new ViewModelProvider(this).get(DeveloperViewModel.class);

        binding = FragmentDeveloperBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDeveloper;
        developerViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
