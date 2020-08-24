package com.otongsoetardjoe.freakmangabrandnew.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.otongsoetardjoe.freakmangabrandnew.R;
import com.otongsoetardjoe.freakmangabrandnew.adapters.view_pager_adapters.ViewPagerMangaMenuTabAdapter;
import com.otongsoetardjoe.freakmangabrandnew.adapters.view_pager_adapters.ViewPagerNekoMenuTabAdapter;
import com.otongsoetardjoe.freakmangabrandnew.databinding.ActivityMainBinding;


public class MainNekoActivity extends AppCompatActivity {
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
                    setTitle("Neko Hen");
                } else if (tab.getPosition() == 1) {
                    setTitle("Neko Jav");
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MainNekoActivity.this, MenuActivity.class));
        finish();
    }

    private void initUI() {
        setTitle("Neko Hen");
        mainBinding.tabHome.addTab(mainBinding.tabHome.newTab().setIcon(getResources().getDrawable(R.drawable.catfill)));
        mainBinding.tabHome.addTab(mainBinding.tabHome.newTab().setIcon(getResources().getDrawable(R.drawable.womanbody)));
        mainBinding.tabHome.setTabIconTint(ColorStateList.valueOf(getResources().getColor(android.R.color.white)));
        mainBinding.viewPagerTabs.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mainBinding.tabHome));
        mainBinding.viewPagerTabs.setAdapter(new ViewPagerNekoMenuTabAdapter(getSupportFragmentManager()));
    }

}