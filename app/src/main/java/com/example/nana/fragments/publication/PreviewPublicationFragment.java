package com.example.nana.fragments.publication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nana.core.BaseFragment;
import com.example.nana.databinding.FragmentPreviewPublicationBinding;

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

        binding.include.textView6.setMovementMethod(new ScrollingMovementMethod());

        getParentFragmentManager().setFragmentResultListener("dataFromPublication", this, (requestKey, result) -> {
            String data = result.getString("MyDescription");
            binding.include.textView6.setText(Html.fromHtml(data));

            data = result.getString("MyTheme");
            binding.include.textView13.setText(data);
        });
    }
}