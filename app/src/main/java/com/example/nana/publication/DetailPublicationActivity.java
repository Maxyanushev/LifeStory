package com.example.nana.publication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.nana.databinding.ActivityDetailPublicationBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailPublicationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.nana.databinding.ActivityDetailPublicationBinding binding = ActivityDetailPublicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle("How to create a post?");

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> newPublication());
    }

    public void newPublication() {
        Intent intent = new Intent(this, PublicationActivity.class);
        startActivity(intent);
    }
}