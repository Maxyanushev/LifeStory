package com.example.nana;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.nana.core.BaseActivity;
import com.example.nana.databinding.ActivityMainBinding;
import com.example.nana.fragments.AddPublicationFragment;
import com.example.nana.fragments.FeedFragment;
import com.example.nana.fragments.HomeFragment;
import com.example.nana.publication.DetailPublicationActivity;
import com.example.nana.publication.PublicationActivity;
import com.example.nana.ui.history.HistoryFragment;
import com.example.nana.ui.instruction.InstructionFragment;
import com.example.nana.ui.news.NewsFragment;
import com.example.nana.ui.profile.ProfileFragment;
import com.example.nana.ui.replenishment.ReplenishmentFragment;
import com.example.nana.ui.settings.SettingsFragment;
import com.example.nana.ui.support.SupportFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    public DrawerLayout drawer;
    public NavigationView navigationView;
    public NavController navController;
    public BottomNavigationView bottomNavigationView;

    public View navHeader;

    public SharedPreferences.Editor editor;
    public SharedPreferences wmbPreference;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch onlineStatus;
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
//        setSupportActionBar(binding.appBarMain.toolbar);

        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_profile, R.id.nav_history, R.id.nav_news,
                R.id.nav_replenishment, R.id.nav_support, R.id.nav_instruction,
                R.id.nav_settings).setOpenableLayout(drawer).build();

//        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);

        navHeader = navigationView.getHeaderView(0);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        onlineStatus = navHeader.findViewById(R.id.online_status);
        onlineStatusText = navHeader.findViewById(R.id.online_status_text);
    }

    @SuppressLint("NonConstantResourceId")
    public void initListeners() {
        binding.appBarMain.fab.setOnClickListener(v -> newDetailPost());

        onlineStatus.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                onlineStatusText.setText(R.string.online_status_on);
            } else {
                onlineStatusText.setText(R.string.online_status_off);
            }
        });

        binding.appBarMain.imageView7.setOnClickListener(v -> drawer.openDrawer(GravityCompat.START));

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;

                case R.id.add:
                    replaceFragment(new AddPublicationFragment());
                    break;

                case R.id.feed:
                    replaceFragment(new FeedFragment());
                    break;
            }
            return true;
        });
    }

    private void newDetailPost() {
        wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        Intent intent;
        if (isFirstRun) {
            // Code to run once
            intent = new Intent(this, DetailPublicationActivity.class);
        } else {
            intent = new Intent(this, PublicationActivity.class);
        }
        startActivity(intent);
        editor = wmbPreference.edit();
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
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profile:
                SelectedActivity.startActivityWithFragment(this, ProfileFragment.class, null);
                break;

            case R.id.nav_history:
                SelectedActivity.startActivityWithFragment(this, HistoryFragment.class, null);
                break;

            case R.id.nav_news:
                SelectedActivity.startActivityWithFragment(this, NewsFragment.class, null);
                break;

            case R.id.nav_replenishment:
                SelectedActivity.startActivityWithFragment(this, ReplenishmentFragment.class, null);
                break;

            case R.id.nav_support:
                SelectedActivity.startActivityWithFragment(this, SupportFragment.class, null);
                break;

            case R.id.nav_instruction:
                SelectedActivity.startActivityWithFragment(this, InstructionFragment.class, null);
                break;

            case R.id.nav_settings:
                SelectedActivity.startActivityWithFragment(this, SettingsFragment.class,null);
                break;

            default:
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment_content_main, fragment);
        transaction.commit();
    }
}