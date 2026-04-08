package com.example.currencyconverter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {

    SwitchCompat themeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        themeSwitch = findViewById(R.id.themeSwitch);

        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        boolean isDark = prefs.getBoolean("darkMode", false);

        themeSwitch.setChecked(isDark);

        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("darkMode", isChecked);
            editor.apply();

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
    }


    public void goBack(View view) {
        finish(); // goes back to MainActivity
    }
}