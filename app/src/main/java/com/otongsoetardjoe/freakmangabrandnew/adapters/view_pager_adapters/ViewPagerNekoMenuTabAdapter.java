package com.otongsoetardjoe.freakmangabrandnew.adapters.view_pager_adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.otongsoetardjoe.freakmangabrandnew.fragments.anime_fragments.*;
import com.otongsoetardjoe.freakmangabrandnew.fragments.anime_fragments.neko_new_releases_mvp.NekoNewReleasesFragment;

/*
 * Created by Rosinante24 on 2019-05-30.
 */
public class ViewPagerNekoMenuTabAdapter extends FragmentStatePagerAdapter {
    public ViewPagerNekoMenuTabAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new NekoNewReleasesFragment();
        } else if (position == 1) {
            return new NekoDiscoverFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
