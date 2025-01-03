package com.reveny.nativecheck.ui.activity;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.reveny.nativecheck.util.ThemeUtil;
import rikka.material.app.MaterialActivity;

public class BaseActivity extends MaterialActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onApplyUserThemeResource(@NonNull Resources.Theme theme, boolean isDecorView) {
        if (!ThemeUtil.isSystemAccent()) {
            theme.applyStyle(ThemeUtil.getColorThemeStyleRes(), true);
        }
    }

    @Override
    public void onApplyTranslucentSystemBars() {
        super.onApplyTranslucentSystemBars();
        Window window = getWindow();
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);
    }
}