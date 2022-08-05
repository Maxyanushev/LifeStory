package com.example.nana.publication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.example.nana.core.BaseActivity;
import com.example.nana.databinding.ActivityDetailPublicationBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailPublicationActivity extends BaseActivity {

    public ActivityDetailPublicationBinding binding;

    private FloatingActionButton fab;
    public Toolbar toolbar;
    public CollapsingToolbarLayout toolBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        initListeners();
    }

    public void init() {
        binding = ActivityDetailPublicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        fab = binding.fab;
        toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle("How to create a post?");
    }

    public void initListeners() {
        fab.setOnClickListener(view -> newPublication());
    }

    public void newPublication() {
        Intent intent = new Intent(this, PublicationActivity.class);
        startActivity(intent);
    }
}