package com.example.nana;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.nana.databinding.ActivityMainBinding;
import com.example.nana.publication.DetailPublicationActivity;
import com.example.nana.publication.PublicationActivity;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private NavController navController;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch onlineStatus;
    private View navHeader;
    private TextView onlineStatusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        initListeners();
    }

    public void init() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        drawer = binding.drawerLayout;
        navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_profile, R.id.nav_music, R.id.nav_games,
                R.id.nav_rave, R.id.nav_anime, R.id.nav_studies,
                R.id.nav_social, R.id.nav_another, R.id.nav_programming,
                R.id.nav_developer).setOpenableLayout(drawer).build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navHeader = navigationView.getHeaderView(0);
        onlineStatus = navHeader.findViewById(R.id.online_status);
        onlineStatusText = navHeader.findViewById(R.id.online_status_text);
    }

    public void initListeners() {
        binding.appBarMain.fab.setOnClickListener(view -> newDetailPost());

        onlineStatus.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                onlineStatusText.setText(R.string.online_status_on);
            } else {
                onlineStatusText.setText(R.string.online_status_off);
            }
        });
    }

    private void newDetailPost() {
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        Intent intent;
        if (isFirstRun) {
            // Code to run once
            intent = new Intent(this, DetailPublicationActivity.class);
        } else {
            intent = new Intent(this, PublicationActivity.class);
        }
        startActivity(intent);
        SharedPreferences.Editor editor = wmbPreference.edit();
        editor.putBoolean("FIRSTRUN", false);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}