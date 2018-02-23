package com.ahengling.itsreadingtime.model;

/**
 * Created by adolfohengling on 2/22/18.
 */

public class Book {

    private String title;
    private Integer nbOfPages;

    public Book(String title, Integer nbOfPages) {
        this.title = title;
        this.nbOfPages = nbOfPages;
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
