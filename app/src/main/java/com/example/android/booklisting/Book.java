package com.example.android.booklisting;

import java.util.List;

/**
 * Created by lilla on 21/07/17.
 */

public class Book {

    String title;
    List<String> authors;
    String publishedDate;

    public Book(String title, List<String> authors, String publishedDate) {
        this.title = title;
        this.authors = authors;
        this.publishedDate = publishedDate;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

}
