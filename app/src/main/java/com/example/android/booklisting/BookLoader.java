package com.example.android.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by lilla on 22/07/17.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private static final String LOG_TAG = BookLoader.class.getName();

    private String url;
    private String noAuthorLabel;
    private String noPublishedDateLabel;

    public BookLoader(Context context, String url, String noAuthorLabel, String noPublishedDateLabel) {
        super(context);
        this.url = url;
        this.noAuthorLabel = noAuthorLabel;
        this.noPublishedDateLabel = noPublishedDateLabel;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if (url == null) {
            return null;
        }

        List<Book> books = QueryUtils.fetchBookData(url, noAuthorLabel, noPublishedDateLabel);
        return books;
    }
}

