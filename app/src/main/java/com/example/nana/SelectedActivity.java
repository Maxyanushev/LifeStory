package com.example.nana;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.nana.core.BaseActivity;

public class SelectedActivity extends BaseActivity {
    private static final String FRAGMENT_NAME = "fragment_name";

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

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
            return;
        }

        Class<? extends Fragment> fragmentName = (Class<? extends Fragment>) extras.getSerializable(FRAGMENT_NAME);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_selected_activity, fragmentName, extras)
                .commit();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}