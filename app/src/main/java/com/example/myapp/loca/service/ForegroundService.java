package com.example.myapp.loca.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.example.myapp.loca.MainActivity;
import com.example.myapp.loca.receiver.UpdateLocateBroadcastReceiver;
import com.example.myapp.loca.R;

public class ForegroundService extends Service {
    public static final String CHANNEL_ID = "FOREGROUND_SERVICE_01";

    public final int NOTIFICATION_ID = 1;
    public final String NOTIFICATION_TITLE = "Loca";
    public final String NOTIFICATION_TEXT = "지금 위치를 기록하세요";

    public ForegroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();

        RemoteViews customLayout = getCustomNotificationLayout();
        Notification notification = getCustomNotification(customLayout);

        startForeground(NOTIFICATION_ID, notification);

        return super.onStartCommand(intent, flags, startId);
    }

    private void setCustomNotificationLayout(RemoteViews customLayout) {
        Intent locateIntent = new Intent(this, UpdateLocateBroadcastReceiver.class);
        locateIntent.setAction("UpdateLocate");

        PendingIntent locatePendingIntent = PendingIntent.getBroadcast(this, 0, locateIntent, 0);

        customLayout.setTextViewText(R.id.textViewTitle, NOTIFICATION_TITLE);
        customLayout.setTextViewText(R.id.textViewText, NOTIFICATION_TEXT);
        customLayout.setOnClickPendingIntent(R.id.icLocate, locatePendingIntent);
    }

    private RemoteViews getCustomNotificationLayout() {
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.custom_notification_small);
        setCustomNotificationLayout(notificationLayout);

        return notificationLayout;
    }

    private NotificationCompat.Builder buildCustomNotification(RemoteViews customLayout) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(customLayout)
                .setContentIntent(notificationPendingIntent);

        return notificationBuilder;
    }

    private Notification getCustomNotification(RemoteViews customLayout) {
        return buildCustomNotification(customLayout).build();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
