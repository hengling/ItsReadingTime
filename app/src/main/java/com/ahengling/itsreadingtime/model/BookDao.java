package com.ahengling.itsreadingtime.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
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

    @Query("SELECT * FROM book WHERE id = :id")
    Book getById(Long id);

    @Insert
    void insertAll(Book... books);

    @Delete
    void delete(Book book);

}
