package com.reveny.nativecheck;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.reveny.nativecheck.util.ThemeUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import rikka.core.os.FileUtils;
import rikka.material.app.LocaleDelegate;

public class App extends Application {
    public static final FutureTask<String> HTML_TEMPLATE = new FutureTask<>(() -> readWebviewHTML("template.html"));
    public static final FutureTask<String> HTML_TEMPLATE_DARK = new FutureTask<>(() -> readWebviewHTML("template_dark.html"));

    private static App instance = null;
    private SharedPreferences pref;

    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    public static App getInstance() {
        return instance;
    }
    public static SharedPreferences getPreferences() { return instance.pref; }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        // Language settings
        boolean useSystemLanguage = pref.getBoolean("use_system_language", true);
        LocaleDelegate.setDefaultLocale(getLocale(useSystemLanguage ? null : "en"));

        AppCompatDelegate.setDefaultNightMode(ThemeUtil.getDarkTheme());

        executorService.submit(HTML_TEMPLATE);
        executorService.submit(HTML_TEMPLATE_DARK);
    }

    public static Locale getLocale(String tag) {
        if (TextUtils.isEmpty(tag) || "SYSTEM".equals(tag)) {
            return LocaleDelegate.getSystemLocale();
        }
        return Locale.forLanguageTag(tag);
    }

    @SuppressLint("NewApi")
    private static String readWebviewHTML(String name) {
        try {
            var input = App.getInstance().getAssets().open("webview/" + name);
            var result = new ByteArrayOutputStream(1024);
            FileUtils.copy(input, result);
            return result.toString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "<html dir\"@dir@\"><body>@body@</body></html>";
        }
    }
}