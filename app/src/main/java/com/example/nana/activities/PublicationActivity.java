package com.example.nana.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.nana.R;
import com.example.nana.core.BaseActivity;
import com.example.nana.databinding.ActivityPublicationBinding;
import com.example.nana.fragments.publication.PublicationFragment;

public class PublicationActivity extends BaseActivity {

    public ActivityPublicationBinding binding;
    public PublicationFragment publicationFragment = new PublicationFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publication);

        init();
        initListeners();
    }

    public void init() {
        binding = ActivityPublicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, publicationFragment)
                .commit();
    }

    public void initListeners() {
        binding.btnBack.setOnClickListener(v -> onBackPressed());
        binding.btnHelp.setOnClickListener(v -> startActivity(new Intent(this, DetailPublicationActivity.class)));
    }
}