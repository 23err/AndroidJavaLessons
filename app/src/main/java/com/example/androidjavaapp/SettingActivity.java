package com.example.androidjavaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingActivity extends BaseActivity {

    private SwitchMaterial switchDarkTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        findViews();
        setCheckedSwitch();
        setCheckedChangeListener();

    }

    private void setCheckedSwitch() {
        if (getThemeId() == R.style.DarkTheme) {
            switchDarkTheme.setChecked(true);
        }
    }

    private void setCheckedChangeListener() {
        switchDarkTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    setThemeId(R.style.DarkTheme);
                } else {
                    setThemeId(R.style.LightTheme);
                }
            }
        });

    }

    private void findViews() {
        switchDarkTheme = findViewById(R.id.switchDarkTheme);
    }
}