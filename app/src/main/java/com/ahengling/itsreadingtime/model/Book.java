package com.ahengling.itsreadingtime.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by adolfohengling on 2/22/18.
 */

@Entity
public class Book {

    @PrimaryKey
    private Long id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "nb_of_pages")
    private Integer nbOfPages;

    public Book() {}

    public Book(Long id, String title, Integer nbOfPages) {
        this.id = id;
        this.title = title;
        this.nbOfPages = nbOfPages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNbOfPages() {
        return nbOfPages;
    }

    public void setNbOfPages(Integer nbOfPages) {
        this.nbOfPages = nbOfPages;
    }
}
