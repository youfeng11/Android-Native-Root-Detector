package com.reveny.nativecheck.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.reveny.nativecheck.R;
import com.reveny.nativecheck.ui.fragment.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.settings_container, new SettingsFragment())
            .commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}