package com.example.nana;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.nana.core.BaseActivity;
import com.example.nana.interfaces.OnBackPressed;

import java.util.List;

public class SelectedActivity extends BaseActivity {

    private static final String TAG = "SelectedActivity";

    private static final String FRAGMENT_NAME = "fragment_name";
    public MutableLiveData<String> toolBarTitle = new MutableLiveData<>();
    TextView toolbarTitle;

    public static void startActivityWithFragment(@NonNull Context context,
                                                 @NonNull Class<? extends Fragment> fragmentClass,
                                                 @Nullable Bundle bundle) {
        context.startActivity(getStartIntent(context, fragmentClass, bundle));
    }

    private static Intent getStartIntent(@NonNull Context context,
                                         @NonNull Class<? extends Fragment> fragmentClass,
                                         @Nullable Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putSerializable(FRAGMENT_NAME, fragmentClass);
        Intent intent = new Intent(context, SelectedActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_selected);

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbarTitle = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbar.setTitle("");

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
            return;
        }

        Class<? extends Fragment> fragmentName = (Class<? extends Fragment>) extras.getSerializable(FRAGMENT_NAME);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_selected_activity, fragmentName, extras)
                .commit();

        toolBarTitle.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s == null) return;

                Log.d(TAG, "onChanged: ");

                toolbarTitle.setText(s);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void setToolBarTitle(String toolBarTitle){
        try {
            toolbarTitle.setText(toolBarTitle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeToolbarVisibility(boolean isVisible) {
        int visibility;
        if (isVisible) {
            visibility = View.VISIBLE;
        } else {
            visibility = View.GONE;
        }
        View toolbar = findViewById(R.id.toolbar);
        if (toolbar == null) {
            return;
        }
        toolbar.setVisibility(visibility);
        findViewById(R.id.toolbar_title).setVisibility(visibility);
    }

    @Override
    public void onBackPressed() {
        boolean handled = false;
        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        for (Fragment f : fragments) {
            if (f instanceof OnBackPressed) {
                handled = ((OnBackPressed) f).onBackPressed();

                if (handled) {
                    break;
                }
            }
        }

        if (!handled) {
            super.onBackPressed();
        }
    }
}