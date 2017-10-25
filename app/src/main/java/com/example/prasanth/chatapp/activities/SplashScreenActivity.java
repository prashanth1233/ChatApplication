package com.example.prasanth.chatapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.example.prasanth.chatapp.R;
import com.example.prasanth.chatapp.utils.PreferenceConnector;

public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handler = new Handler();


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(PreferenceConnector.readString(SplashScreenActivity.this, "userName", ""))) {
                    Intent intent = new Intent(SplashScreenActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashScreenActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        handler.postDelayed(runnable, 4000);
    }
}

