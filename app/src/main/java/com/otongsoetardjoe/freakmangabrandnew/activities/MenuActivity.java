package com.otongsoetardjoe.freakmangabrandnew.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.otongsoetardjoe.freakmangabrandnew.R;
import com.otongsoetardjoe.freakmangabrandnew.databinding.ActivityMenuBinding;
import com.otongsoetardjoe.freakmangabrandnew.databinding.ActivitySplashBinding;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMenuBinding menuBinding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(menuBinding.getRoot());
        menuBinding.textNekoMenu.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, MainNekoActivity.class));
            finish();
        });
        menuBinding.textNhenMenu.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, MainActivity.class));
            finish();
        });
    }
}