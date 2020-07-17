package com.otongsoetardjoe.freakmangabrandnew;

import android.content.res.ColorStateList;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.otongsoetardjoe.freakmangabrandnew.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        initUI();
        initEvent();
    }

    private void initEvent() {
        mainBinding.tabHome.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mainBinding.viewPagerTabs.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    setTitle("New releases");
                } else if (tab.getPosition() == 1) {
                    setTitle("Discover");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initUI() {
        setTitle("New releases");
        mainBinding.tabHome.addTab(mainBinding.tabHome.newTab().setIcon(getResources().getDrawable(R.drawable.ic_home_white_24dp)));
        mainBinding.tabHome.addTab(mainBinding.tabHome.newTab().setIcon(getResources().getDrawable(R.drawable.ic_view_list_white_24dp)));
        mainBinding.tabHome.setTabIconTint(ColorStateList.valueOf(getResources().getColor(android.R.color.white)));
        mainBinding.viewPagerTabs.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mainBinding.tabHome));
        mainBinding.viewPagerTabs.setAdapter(new ViewPagerMangaMenuTabAdapter(getSupportFragmentManager()));
    }

}