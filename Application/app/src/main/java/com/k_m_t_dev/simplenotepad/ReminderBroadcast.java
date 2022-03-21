package com.k_m_t_dev.simplenotepad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifylemubit")

                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Notification")
                .setContentText("You has 1 notification from notepad ")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(200, builder.build());
    }
}