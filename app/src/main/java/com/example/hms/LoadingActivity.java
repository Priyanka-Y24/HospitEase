package com.example.hms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class LoadingActivity extends Activity {

    private static final long SPLASH_TIME_OUT = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ImageView imageView = findViewById(R.id.logoImageView); // Replace with your actual view ID
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imageView.startAnimation(fadeIn);

        new Handler().postDelayed(this::run, SPLASH_TIME_OUT);
    }

    private void run() {
        // This code will run after the SPLASH_TIME_OUT milliseconds.
        // Start your main activity here.
        Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
        startActivity(intent);

        // Close the LoadingActivity to prevent returning to it.
        finish();
    }
}
