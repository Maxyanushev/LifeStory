package com.example.nana.publication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nana.R;
import com.example.nana.databinding.ActivityPublicationBinding;

public class PublicationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publication);

        init();
    }

    public void init() {
        ActivityPublicationBinding binding = ActivityPublicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle(R.string.new_publication);
    }
}