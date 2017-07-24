package com.example.android.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lilla on 22/07/17.
 */

public class BookListingAdapter extends ArrayAdapter<Book> {
    public BookListingAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }

        Book currentBook = getItem(position);

        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        titleView.setText(currentBook.getTitle());

        TextView authorsView = (TextView) listItemView.findViewById(R.id.authors);
        authorsView.setText(TextUtils.join(", " , currentBook.getAuthors()));

        TextView publishedDateLabelView = (TextView) listItemView.findViewById(R.id.publishedDateLabel);
        publishedDateLabelView.setText(R.string.published_date_label);

        TextView publishedDateView = (TextView) listItemView.findViewById(R.id.publishedDate);
        publishedDateView.setText(currentBook.getPublishedDate());

        return listItemView;
    }


}
