package com.ahengling.itsreadingtime.events;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.ahengling.itsreadingtime.R;
import com.ahengling.itsreadingtime.model.Book;
import com.ahengling.itsreadingtime.util.Constants;

/**
 * Created by adolfohengling on 2/24/18.
 */

public class AlarmReminder extends BroadcastReceiver {

    private static int notificationId = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        String bookTitle = intent.getStringExtra("book");
        StringBuilder stringBuilder = new StringBuilder(context.getString(R.string.msg_notification))
                .append(" ").append(bookTitle);

        String nChannel = Constants.NOTIFICATION.CHANNEL_ID;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, nChannel)
                .setChannelId(nChannel)
                .setSmallIcon(R.drawable.ic_book)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(stringBuilder.toString())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        createNotificationChannel(context);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, mBuilder.build());

        notificationId += 1;
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String id = Constants.NOTIFICATION.CHANNEL_ID;
            String description = Constants.NOTIFICATION.CHANNEL_DESCRIPTION;
            CharSequence name = context.getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(id, name, importance);
            channel.setDescription(description);

            ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                    .createNotificationChannel(channel);
        }
    }
}
