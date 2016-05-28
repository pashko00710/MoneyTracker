package com.example.moneytracker.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

public class DataBaseApp extends Application {
    private static SharedPreferences preferences;
    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this)
                .openDatabasesOnInit(true).build());

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public static void setAuthToken(String token){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ConstantManager.TOKEN_KEY, token);
        editor.apply();
    }

    public static String getAuthKey(){
        return preferences.getString(ConstantManager.TOKEN_KEY,"");
    }

    public static void setGoogleToken(Context context, String token) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ConstantManager.GOOGLE_TOKEN_KEY, token);
        editor.apply();
    }

    public static String getGoogleToken(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(ConstantManager.GOOGLE_TOKEN_KEY, "");
    }
}
