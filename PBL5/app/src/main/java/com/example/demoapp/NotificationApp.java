package com.example.demoapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

public class NotificationApp extends Application {
    public static final String CHANNEL_ID="push_notifiction_id";
    @Override
    public void onCreate() {
        super.onCreate();
        createChanelNotificaltion();
    }

    private void createChanelNotificaltion()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            Log.d("Debug","noti appp");
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,"PushNotification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel((channel));
        }
    }
}
