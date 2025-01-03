package com.reveny.nativecheck.ui.activity;

import android.os.Bundle;

import com.reveny.nativecheck.R;
import com.reveny.nativecheck.databinding.ActivityMainBinding;
import com.reveny.nativecheck.ui.fragment.HomeFragment;

public class MainActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        com.reveny.nativecheck.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
    }
}