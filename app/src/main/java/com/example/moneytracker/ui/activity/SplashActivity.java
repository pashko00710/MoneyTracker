package com.example.moneytracker.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.example.moneytracker.R;
import com.example.moneytracker.util.DataBaseApp;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends AppCompatActivity {

    @AfterViews
    void ready() {
        doInBackground();
    }

    @Background(delay=3000)
    void doInBackground() {
        if (DataBaseApp.getAuthKey().equals("")){
            startActivity(new Intent(this, LoginActivity_.class));
        }
        else{
            startActivity(new Intent(this, MainActivity_.class));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
