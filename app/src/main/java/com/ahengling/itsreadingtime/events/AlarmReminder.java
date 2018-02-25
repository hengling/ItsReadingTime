package com.ahengling.itsreadingtime.events;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.ahengling.itsreadingtime.R;
import com.ahengling.itsreadingtime.config.db.AppDatabase;
import com.ahengling.itsreadingtime.model.Book;
import com.ahengling.itsreadingtime.model.BookDao;
import com.ahengling.itsreadingtime.util.Constants;

/**
 * Created by adolfohengling on 2/24/18.
 */

public class AlarmReminder extends BroadcastReceiver {

    private static Integer notificationId;

    private static int getNextNotificationId() {
        if (notificationId == null) {
            notificationId = 0;
        } else {
            notificationId++;
        }
        return notificationId;
    }

    private static class findBookById extends AsyncTask<Long, Void, Book> {

        private Context context;

        public findBookById(Context context) {
            super();
            this.context = context;
        }

        @Override
        protected Book doInBackground(Long... longs) {
            BookDao bookDao = AppDatabase.getInstance(this.context).bookDao();
            return bookDao.getById(longs[0]);
        }

        @Override
        protected void onPostExecute(Book book) {
            if (book == null) {
                return;
            }

            NotificationCompat.Builder mBuilder = createNotification(context, book);
            createNotificationChannel(context);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(getNextNotificationId(), mBuilder.build());
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!intent.hasExtra(Constants.EXTRAS.BOOK_ID)) {
            return;
        }

        Long bookId = intent.getLongExtra(Constants.EXTRAS.BOOK_ID, -1L);
        new findBookById(context).execute(bookId);
    }

    private static NotificationCompat.Builder createNotification(Context context, Book book) {
        StringBuilder stringBuilder = new StringBuilder(context.getString(R.string.msg_notification))
                .append(" ").append(book.getTitle()).append(" =D");

        String nChannel = Constants.NOTIFICATION.CHANNEL_ID;
        return new NotificationCompat.Builder(context, nChannel)
                .setChannelId(nChannel)
                .setSmallIcon(R.drawable.ic_book)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(stringBuilder.toString())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    private static void createNotificationChannel(Context context) {
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
