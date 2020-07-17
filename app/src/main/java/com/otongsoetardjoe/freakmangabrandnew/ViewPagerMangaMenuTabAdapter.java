package com.otongsoetardjoe.freakmangabrandnew;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.otongsoetardjoe.freakmangabrandnew.fragments.DiscoverFragment;
import com.otongsoetardjoe.freakmangabrandnew.fragments.NewReleasesFragment;

/*
 * Created by Rosinante24 on 2019-05-30.
 */
public class ViewPagerMangaMenuTabAdapter extends FragmentStatePagerAdapter {
    public ViewPagerMangaMenuTabAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new NewReleasesFragment();
        } else if (position == 1) {
            return new DiscoverFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
