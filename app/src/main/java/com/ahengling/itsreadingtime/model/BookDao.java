package com.ahengling.itsreadingtime.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by adolfohengling on 2/22/18.
 */

@Dao
public interface BookDao {

    @Query("SELECT * FROM book")
    List<Book> getAll();

    @Insert
    void insertAll(Book... books);

}
