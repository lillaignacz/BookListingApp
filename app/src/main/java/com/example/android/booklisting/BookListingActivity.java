package com.example.android.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookListingActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final String LOG_TAG = BookListingActivity.class.getName();
    private static final int BOOK_LOADER_ID = 1;
    String searchKeyword = "";
    EditText searchField;
    private String GOOGLE_BOOKS_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private BookListingAdapter bookListingAdapter;
    private TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.booklisting_activity);

        final ListView bookListView = (ListView) findViewById(R.id.list_view);

        emptyTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(emptyTextView);

        searchField = (EditText) findViewById(R.id.search_field);

        final Button searchButton = (Button) findViewById(R.id.search_button);

        View progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Hide virtual keyboard when Search button gets clicked
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                // Get the keyword that the user typed
                searchKeyword = searchField.getText().toString();

                bookListingAdapter = new BookListingAdapter(BookListingActivity.this, new ArrayList<Book>());

                bookListView.setAdapter(bookListingAdapter);

                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    LoaderManager loaderManager = getLoaderManager();
                    Bundle params = new Bundle(3);
                    params.putString("search", searchKeyword);
                    params.putString("noAuthorLabel", getString(R.string.no_author));
                    params.putString("noPublishedDateLabel", getString(R.string.no_published_date));
                    if (loaderManager.getLoader(BOOK_LOADER_ID) != null) {
                        loaderManager.restartLoader(BOOK_LOADER_ID, params, BookListingActivity.this);
                        bookListingAdapter.clear();
                    } else {

                        loaderManager.initLoader(BOOK_LOADER_ID, params, BookListingActivity.this);
                    }
                } else {
                    emptyTextView.setText(R.string.no_internet_connection);
                    bookListingAdapter.clear();
                }
            }
        });
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle bundle) {
        View progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        return new BookLoader(this, GOOGLE_BOOKS_REQUEST_URL + bundle.getString("search"),
                bundle.getString("noAuthorLabel"), bundle.getString(("noPublishedDateLabel")));
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        View progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        if (books != null && !books.isEmpty()) {
            bookListingAdapter.addAll(books);
        } else {
            emptyTextView.setText(R.string.no_books_found);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        bookListingAdapter.clear();
    }
}
