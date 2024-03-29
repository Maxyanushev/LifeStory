package com.example.nana.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import com.example.nana.R;
import com.example.nana.core.BaseActivity;
import com.example.nana.databinding.ActivityMainBinding;
import com.example.nana.fragments.navigation.bottom.FeedFragment;
import com.example.nana.fragments.navigation.bottom.NotificationFragment;
import com.example.nana.fragments.navigation.bottom.SearchFragment;
import com.example.nana.fragments.navigation.drawer.history.HistoryFragment;
import com.example.nana.fragments.navigation.drawer.instruction.InstructionFragment;
import com.example.nana.fragments.navigation.drawer.news.NewsFragment;
import com.example.nana.fragments.navigation.drawer.profile.ProfileFragment;
import com.example.nana.fragments.navigation.drawer.replenishment.ReplenishmentFragment;
import com.example.nana.fragments.navigation.drawer.settings.SettingsFragment;
import com.example.nana.fragments.navigation.drawer.support.SupportFragment;
import com.example.nana.utilites.Constants;
import com.example.nana.utilites.PreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    public DrawerLayout drawer;
    public NavigationView navigationView;
    public NavController navController;
    public BottomNavigationView bottomNavigationView;

    public View navHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        initListeners();
    }

    public void init() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
        navigationView.setBackgroundColor(Color.argb(255, 20,34, 50));

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_profile, R.id.nav_history, R.id.nav_news,
                R.id.nav_replenishment, R.id.nav_support, R.id.nav_instruction,
                R.id.nav_settings).setOpenableLayout(drawer).build();

        navHeader = navigationView.getHeaderView(0);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        TextView usernameText = navHeader.findViewById(R.id.name_text);
        // put the username in the sidebar name field
        usernameText.setText(preferenceManager.getString(Constants.KEY_NAME));

        replaceFragment(new FeedFragment());
    }

    @SuppressLint("NonConstantResourceId")
    public void initListeners() {

        binding.appBarMain.imageButton3.setOnClickListener(v -> drawer.openDrawer(GravityCompat.START));
        binding.appBarMain.imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChatActivity.class);
            startActivity(intent);
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new FeedFragment());
                    break;

                case R.id.search:
                    replaceFragment(new SearchFragment());
                    break;

                case R.id.add:
                    replaceActivity(PublicationActivity.class);
                    break;

                case R.id.notification:
                    replaceFragment(new NotificationFragment());
                    break;

                case R.id.profile:
                    replaceFragment(new ProfileFragment(false));
                    break;
            }
            return true;
        });
    }

//    private void newDetailPost() {
//        wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
//        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
//        if (isFirstRun) {
//            replaceActivity(DetailPublicationActivity.class);
//        } else {
//            replaceActivity(PublicationActivity.class);
//        }
//        editor = wmbPreference.edit();
//        editor.putBoolean("FIRSTRUN", false);
//        editor.apply();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

    public void replaceActivity(Class activity) {
        startActivity(new Intent(this, activity));
    }
}