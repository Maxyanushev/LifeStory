package com.example.nana.publication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nana.MainActivity;
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

        binding.btnBack.setOnClickListener(v -> onActivity("MainActivity"));

        binding.btnHelp.setOnClickListener(v -> onActivity("DetailPublicationActivity"));
    }

    public void onActivity(String o) {
        Intent intent;
        switch (o) {
            case "MainActivity":
                intent = new Intent(this, MainActivity.class);
                break;
            case "DetailPublicationActivity":
                intent = new Intent(this, DetailPublicationActivity.class);
                break;
            default:
                intent = new Intent(this, PublicationActivity.class);
                break;
        } startActivity(intent);
    }
}