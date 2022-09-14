package com.example.nana.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.nana.ui.profile.fragments.PostsFragment;
import com.example.nana.ui.profile.fragments.SavesFragment;

public class ViewPagerAdapterProfile extends FragmentStateAdapter {

    public ViewPagerAdapterProfile(@NonNull Fragment fragment) {
        super(fragment);
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new PostsFragment();
        } else {
            return new SavesFragment();
        }
    }
}
