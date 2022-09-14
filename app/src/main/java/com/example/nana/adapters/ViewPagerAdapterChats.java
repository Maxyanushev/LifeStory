package com.example.nana.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.nana.chat.fragments.GroupChatsFragment;
import com.example.nana.chat.fragments.PersonalChatsFragment;

public class ViewPagerAdapterChats extends FragmentStateAdapter {

    public ViewPagerAdapterChats(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 1:
                return new GroupChatsFragment();
            case 0:
            default:
                return new PersonalChatsFragment();
        }
    }
    @Override
    public int getItemCount() {
        return 2;
    }
}
