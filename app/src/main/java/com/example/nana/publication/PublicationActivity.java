package com.example.nana.publication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nana.MainActivity;
import com.example.nana.R;
import com.example.nana.databinding.ActivityPublicationBinding;
import com.example.nana.databinding.FragmentPublicationBinding;
import com.example.nana.publication.fragments.PublicationFragment;

public class PublicationActivity extends AppCompatActivity {

    public ActivityPublicationBinding binding;
    public FragmentPublicationBinding publicationBinding;

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

        publicationBinding = FragmentPublicationBinding.inflate(getLayoutInflater());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, publicationFragment)
                .commit();
    }

    public void initListeners() {
        binding.btnBack.setOnClickListener(v -> openActivity("MainActivity"));
        binding.btnHelp.setOnClickListener(v -> openActivity("DetailPublicationActivity"));
    }

    public void openActivity(String activity) {
        Intent intent = null;
        if (activity.equals("MainActivity")) {
            intent = new Intent(this, MainActivity.class);
        } else if (activity.equals("DetailPublicationActivity")) {
            intent = new Intent(this, DetailPublicationActivity.class);
        } startActivity(intent);
    }
}