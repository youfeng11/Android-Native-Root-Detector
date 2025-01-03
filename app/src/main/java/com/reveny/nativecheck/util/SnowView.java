package com.reveny.nativecheck.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import com.reveny.nativecheck.App;
import com.reveny.nativecheck.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnowView extends View {
    private static final int SNOWFLAKE_COUNT = 75;

    private static final float MIN_SCALE_TYPE_1 = 0.01f;
    private static final float MAX_SCALE_TYPE_1 = 0.03f;

    private static final float MIN_SCALE_TYPE_2 = 0.003f;
    private static final float MAX_SCALE_TYPE_2 = 0.006f;

    private static final int MAX_ALPHA = 255;
    private static final int MIN_ALPHA = 50;

    private static final int MIN_LIFETIME = 7000;
    private static final int MAX_LIFETIME = 15000;

    private final List<Snowflake> snowflakes = new ArrayList<>();
    private Drawable snowflakeDrawableType1;
    private Drawable snowflakeDrawableType2;
    private final Random random = new Random();
    private final Handler handler = new Handler();

    private boolean isDarkMode;

    public SnowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initDrawables();

        String currentTheme = App.getPreferences().getString("dark_theme", ThemeUtil.MODE_NIGHT_FOLLOW_SYSTEM);
        isDarkMode = ThemeUtil.getDarkTheme(currentTheme) == AppCompatDelegate.MODE_NIGHT_YES;

        startSnowflakeSpawner();
    }

    private void startSnowflakeSpawner() {
        handler.postDelayed(() -> {
            createSnowflake(getWidth(), getHeight());
            startSnowflakeSpawner();
        }, 500 + random.nextInt(1000));
    }

    private void initDrawables() {
        snowflakeDrawableType1 = getResources().getDrawable(R.drawable.snowflake, null);
        snowflakeDrawableType2 = getResources().getDrawable(R.drawable.snowflake2, null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        snowflakes.clear();
        for (int i = 0; i < SNOWFLAKE_COUNT; i++) {
            createSnowflake(w, h);
        }
    }

    private void createSnowflake(int width, int height) {
        if (width <= 0 || height <= 0) return;

        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int speed = 1 + random.nextInt(5);

        // Adjust spawn probability based on theme
        boolean isType2 = isDarkMode ? random.nextInt(100) < 70 : random.nextInt(100) < 30;
        Drawable drawable = isType2 ? snowflakeDrawableType2 : snowflakeDrawableType1;

        float minScale = isType2 ? MIN_SCALE_TYPE_2 : MIN_SCALE_TYPE_1;
        float maxScale = isType2 ? MAX_SCALE_TYPE_2 : MAX_SCALE_TYPE_1;

        float scale = minScale + (maxScale - minScale) * random.nextFloat();
        int alpha = MIN_ALPHA + random.nextInt(MAX_ALPHA - MIN_ALPHA + 1);
        int fadeRate = 1 + random.nextInt(3);
        int lifetime = MIN_LIFETIME + random.nextInt(MAX_LIFETIME - MIN_LIFETIME + 1);

        snowflakes.add(new Snowflake(x, y, speed, scale, alpha, fadeRate, lifetime, drawable));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < snowflakes.size(); i++) {
            Snowflake snowflake = snowflakes.get(i);
            snowflake.update(getHeight());

            if (snowflake.lifetime <= 0) {
                snowflakes.remove(i);
                createSnowflake(getWidth(), getHeight());
                continue;
            }

            int width = Math.min((int) (snowflake.drawable.getIntrinsicWidth() * snowflake.scale), getWidth());
            int height = Math.min((int) (snowflake.drawable.getIntrinsicHeight() * snowflake.scale), getHeight());

            snowflake.drawable.setAlpha(snowflake.alpha);
            snowflake.drawable.setBounds(snowflake.x, snowflake.y, snowflake.x + width, snowflake.y + height);

            snowflake.drawable.draw(canvas);
        }

        postInvalidateOnAnimation();
    }

    private static class Snowflake {
        int x, y, speed, alpha, fadeRate, lifetime;
        float scale;
        Drawable drawable;

        public Snowflake(int x, int y, int speed, float scale, int alpha, int fadeRate, int lifetime, Drawable drawable) {
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.scale = scale;
            this.alpha = alpha;
            this.fadeRate = fadeRate;
            this.lifetime = lifetime;
            this.drawable = drawable;
        }

        public void update(int height) {
            y += speed;

            alpha -= fadeRate;
            if (alpha < 0) alpha = 0;

            lifetime -= 16;

            if (y > height) {
                y = -50;
                x = new Random().nextInt(height);
            }
        }
    }
}