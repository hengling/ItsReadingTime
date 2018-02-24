package com.ahengling.itsreadingtime.events;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
        Book book = (Book) intent.getSerializableExtra("book");
        StringBuilder stringBuilder = new StringBuilder(context.getString(R.string.msg_notification))
                .append(" ").append(book.getTitle());

        String nChannel = Constants.NOTIFICATION.CHANNEL_ID;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, nChannel)
                .setSmallIcon(R.drawable.ic_book)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(stringBuilder.toString())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, mBuilder.build());

        notificationId += 1;
    }
}
