package com.example.demoapp;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class PushNotificationServirce extends FirebaseMessagingService{
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        RemoteMessage.Notification notification=message.getNotification();
        if(notification==null) {
            Log.d("Debug","no dtaa");
            return;
        }
        String strTitle=notification.getTitle();
        String strMessage =notification.getBody();
        Log.d("Debug1",strTitle);
        Log.d("Debug1",strMessage);
        sendNotifcation(strTitle, strMessage);
    }

    private void sendNotifcation(String strTitle, String strMessage) {
        Intent intent=new Intent(this,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder notificationBuiled =new NotificationCompat.Builder(this,NotificationApp.CHANNEL_ID)
                .setContentTitle(strTitle)
                .setContentText(strMessage)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                ;

        Notification notification=notificationBuiled.build();
        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager  != null) {

        notificationManager.notify(1,notification);
        }

    }

}
