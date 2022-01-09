package com.surya.scheduler.app;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.text.style.UpdateAppearance;

import androidx.annotation.RequiresApi;

public class surya extends Application {

    // Id for the channels
    public static final String UPDATE_CHANNEL = "Update Notification";
    public static final String ADDED_CHANNEL = "Add/Edit Notification";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    // method to create notification channels for the application
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel1 = new NotificationChannel(UPDATE_CHANNEL, UPDATE_CHANNEL, NotificationManager.IMPORTANCE_HIGH);
            NotificationChannel notificationChannel2 = new NotificationChannel(ADDED_CHANNEL, ADDED_CHANNEL, NotificationManager.IMPORTANCE_DEFAULT);

            // adding the channels to the notification manager
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel1);
            notificationManager.createNotificationChannel(notificationChannel2);
        }
    }
}
