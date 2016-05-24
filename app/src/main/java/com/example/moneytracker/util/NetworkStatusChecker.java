package com.example.moneytracker.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Павел on 18.05.2016.
 */
public class NetworkStatusChecker {

    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_LOGIN_ALREADY = "Login busy already";

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
