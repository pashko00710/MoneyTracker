package com.example.moneytracker.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.NotificationCompat;

import com.example.moneytracker.R;
import com.example.moneytracker.ui.activity.MainActivity;

public class NotificationUtil {
//    SwitchPreference prefEnableNotificationsKey, prefEnableSoundKey,
//            prefEnableVibrateKey, prefEnableIndicatorKey;
    private static final int NOTIFICATION_ID = 4004;

    public static void updateNotifications(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String displayNotificationsKey = context.getString(R.string.pref_enable_notifications_key);
        String prefEnableSoundKey = context.getString(R.string.pref_enable_sound_key);
        String prefEnableVibrateKey = context.getString(R.string.pref_enable_vibrate_key);
        String prefEnableIndicatorKey = context.getString(R.string.pref_enable_indicator_key);
        boolean displayNotifications = prefs.getBoolean(displayNotificationsKey,
                Boolean.parseBoolean(context.getString(R.string.pref_enable_notifications_default)));
        boolean prefEnableSound = prefs.getBoolean(prefEnableSoundKey,
                Boolean.parseBoolean(context.getString(R.string.pref_enable_notifications_default)));
        boolean prefEnableVibrate = prefs.getBoolean(prefEnableVibrateKey,
                Boolean.parseBoolean(context.getString(R.string.pref_enable_notifications_default)));
        boolean prefEnableIndicator = prefs.getBoolean(prefEnableIndicatorKey,
                Boolean.parseBoolean(context.getString(R.string.pref_enable_notifications_default)));

        if (displayNotifications) {
            NotificationCompat.Builder builder = new
                    NotificationCompat.Builder(context);

            Intent i = new Intent(context, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent intent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(intent);

            builder.setSmallIcon(R.mipmap.ic_launcher);
            if(prefEnableIndicator) builder.setLights(Color.CYAN, 850, 2000);
            if(prefEnableVibrate) builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            if(prefEnableSound) builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            builder.setAutoCancel(true);

            String title = context.getString(R.string.app_name);
            String contentText = context.getResources().getString(R.string.notification_message);
            Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            builder.setLargeIcon(largeIcon);
            builder.setContentTitle(title);
            builder.setContentText(contentText);

            Notification notification = builder.build();
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(NOTIFICATION_ID, notification);
        }
    }
}
