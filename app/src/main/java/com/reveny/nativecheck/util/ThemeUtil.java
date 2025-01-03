package com.reveny.nativecheck.util;

import android.content.SharedPreferences;

import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.color.DynamicColors;
import com.reveny.nativecheck.App;
import com.reveny.nativecheck.R;

import java.util.HashMap;
import java.util.Map;

public class ThemeUtil {
    private static final Map<String, Integer> colorThemeMap = new HashMap<>();
    private static final SharedPreferences preferences = App.getPreferences();

    public static final String MODE_NIGHT_FOLLOW_SYSTEM = "MODE_NIGHT_FOLLOW_SYSTEM";
    public static final String MODE_NIGHT_NO = "MODE_NIGHT_NO";
    public static final String MODE_NIGHT_YES = "MODE_NIGHT_YES";

    static {
        colorThemeMap.put("MATERIAL_INDIGO", R.style.ThemeOverlay_MaterialIndigo);
    }

    public static boolean isSystemAccent() {
        return DynamicColors.isDynamicColorAvailable() && preferences.getBoolean("follow_system_accent", true);
    }

    public static String getColorTheme() {
        return isSystemAccent() ? "SYSTEM" : preferences.getString("theme_color", "COLOR_INDIGO");
    }

    @StyleRes
    public static int getColorThemeStyleRes() {
        Integer themeRes = colorThemeMap.getOrDefault(getColorTheme(), R.style.ThemeOverlay_MaterialIndigo);
        return themeRes != null ? themeRes : R.style.ThemeOverlay_MaterialIndigo;
    }

    public static int getDarkTheme(String mode) {
        return switch (mode) {
            default -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
            case MODE_NIGHT_YES -> AppCompatDelegate.MODE_NIGHT_YES;
            case MODE_NIGHT_NO -> AppCompatDelegate.MODE_NIGHT_NO;
        };
    }

    public static int getDarkTheme() {
        return getDarkTheme(preferences.getString("dark_theme", MODE_NIGHT_FOLLOW_SYSTEM));
    }
}