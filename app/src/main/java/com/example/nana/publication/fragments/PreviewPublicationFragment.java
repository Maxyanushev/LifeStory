package com.example.nana.publication.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nana.R;
import com.example.nana.core.BaseFragment;
import com.example.nana.databinding.FragmentPreviewPublicationBinding;
import com.example.nana.databinding.FragmentPublicationBinding;

import org.w3c.dom.Text;

public class PreviewPublicationFragment extends BaseFragment {

    public FragmentPreviewPublicationBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPreviewPublicationBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("dataFromPublication", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String data = result.getString("MyArg");
                binding.include.textView6.setText(data);
            }
        });
    }
}