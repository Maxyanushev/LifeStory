package com.example.nana.fragments.publication;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nana.R;
import com.example.nana.core.BaseFragment;
import com.example.nana.databinding.FragmentPublicationBinding;

public class PublicationFragment extends BaseFragment {

    private FragmentPublicationBinding binding;
    private final PreviewPublicationFragment previewPublicationFragment = new PreviewPublicationFragment();

    Bundle bundle = new Bundle();
    String description = "";
    String theme = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPublicationBinding.inflate(inflater, container, false);

        init();
        initListeners();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void init() {

    }

    @SuppressLint("SetTextI18n")
    private void initListeners() {
        binding.cardButtonNext.setOnClickListener(v -> {
            description = binding.etEnterText.getText().toString();
            bundle.putString("MyDescription", description);

            theme = binding.etTheme.getText().toString();
            bundle.putString("MyTheme", theme);

            getParentFragmentManager().setFragmentResult("dataFromPublication", bundle);

            FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, previewPublicationFragment);
            fragmentTransaction.commit();
        });

        binding.cardTextBold.setOnClickListener(v -> {
            binding.etEnterText.setText(binding.etEnterText.getText() + "<font><b>" + "" + "</b></font>");
            binding.etEnterText.setSelection(binding.etEnterText.length() - 11);
        });

        binding.cardTextItalic.setOnClickListener(v -> {
            binding.etEnterText.setText(binding.etEnterText.getText() + "<font><i>" + "" + "</i></font>");
            binding.etEnterText.setSelection(binding.etEnterText.length() - 11);
        });

        binding.cardTextUnderline.setOnClickListener(v -> {
            binding.etEnterText.setText(binding.etEnterText.getText() + "<font><u>" + "" + "</u></font>");
            binding.etEnterText.setSelection(binding.etEnterText.length() - 11);
        });

        binding.cardTextStrike.setOnClickListener(v -> {
            binding.etEnterText.setText(binding.etEnterText.getText() + "<font><strike>" + "" + "</strike></font>");
            binding.etEnterText.setSelection(binding.etEnterText.length() - 16);
        });
    }
}