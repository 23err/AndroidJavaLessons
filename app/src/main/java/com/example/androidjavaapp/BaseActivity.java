package com.example.androidjavaapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    public static final String CONFIG_SHARED = "configuration";
    public static final String THEME_ID = "themeId";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getThemeId());
    }

    public int getThemeId() {
        SharedPreferences sp = getSharedPreferences(CONFIG_SHARED, MODE_PRIVATE);
        return sp.getInt(THEME_ID, R.style.LightTheme);
    }

    public void setThemeId(int themeId){
        SharedPreferences sp = getSharedPreferences(CONFIG_SHARED, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(THEME_ID, themeId);
        edit.apply();
        recreate();
    }
}
