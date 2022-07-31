package com.example.nana.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.nana.ui.profile.ProfileFragment;
import com.example.nana.ui.profile.fragments.PostsFragment;
import com.example.nana.ui.profile.fragments.SavesFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PostsFragment();
            case 1:
                return new SavesFragment();
            default:
                return new PostsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
