package com.example.nana.publication.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.nana.R;
import com.example.nana.core.BaseFragment;
import com.example.nana.databinding.FragmentPublicationBinding;

public class PublicationFragment extends BaseFragment {

    private FragmentPublicationBinding binding;

    private final PreviewPublicationFragment previewPublicationFragment = new PreviewPublicationFragment();

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

    private void initListeners() {
        binding.cardButtonNext.setOnClickListener(v -> {

            Bundle bundle = new Bundle();
            String text = binding.etEnterText.getText().toString();
            bundle.putString("MyArg", text);
            getParentFragmentManager().setFragmentResult("dataFromPublication", bundle);

            FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, previewPublicationFragment);
            fragmentTransaction.commit();
        });
    }
}