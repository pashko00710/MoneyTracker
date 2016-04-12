package com.example.moneytracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String MY_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(MY_TAG, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MY_TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(MY_TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(MY_TAG, "onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(MY_TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(MY_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(MY_TAG, "onDestroy");
    }
}
