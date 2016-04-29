package com.example.moneytracker;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends Activity {
    @AfterViews
    void ready() {
        doInBackground();
    }

    @Background(delay=3000)
    void doInBackground() {
        MainActivity_.intent(this).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
