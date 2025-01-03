package com.reveny.nativecheck.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;

import com.reveny.nativecheck.R;

import java.util.Objects;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getPreferenceManager().getSharedPreferences()).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Objects.requireNonNull(getPreferenceManager().getSharedPreferences()).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(@NonNull SharedPreferences sharedPreferences, @Nullable String key) {
        Log.i("RevenyDetector", "onSharedPreferenceChanged: " + key + " = " + sharedPreferences.getAll().get(key));
    }
}
