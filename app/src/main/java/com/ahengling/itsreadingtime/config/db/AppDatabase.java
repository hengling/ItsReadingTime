package com.ahengling.itsreadingtime.config.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.ahengling.itsreadingtime.model.Book;
import com.ahengling.itsreadingtime.model.BookDao;
import com.ahengling.itsreadingtime.util.Constants;

/**
 * Created by adolfohengling on 2/21/18.
 */

@Database(entities = {Book.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    public abstract BookDao bookDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null || !instance.isOpen()) {
            instance = Room.databaseBuilder(context, AppDatabase.class, Constants.DATABASE.NAME)
                    .build();
        }
        return instance;
    }
}
