package com.ahengling.itsreadingtime.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ahengling.itsreadingtime.R;
import com.ahengling.itsreadingtime.SetupReminderActivity;
import com.ahengling.itsreadingtime.model.Book;

import java.util.List;

/**
 * Created by adolfohengling on 2/22/18.
 */

public class BooksListAdapter extends ArrayAdapter<Book> {

    private final List<Book> books;

    public BooksListAdapter(@NonNull Context context, List<Book> books) {
        super(context, -1, books);
        this.books = books;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = createBookItemView(parent);

        fillTextViewWithValue(view, R.id.title_text_view, books.get(position).getTitle());
        fillTextViewWithValue(view, R.id.pages_text_view, getNbOfPagesAsString(view, position));

        createSetupReminderClickListener(view, position);

        return view;
    }

    private View createBookItemView(ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return inflater.inflate(R.layout.book_list_item, parent, false);
    }

    private void fillTextViewWithValue(View view, int resId, String value) {
        TextView titleTextView = (TextView) view.findViewById(resId);
        titleTextView.setText(value);
    }

    private String getNbOfPagesAsString(View view, int position) {
        String pages = " " + view.getResources().getString(R.string.pages);
        return books.get(position).getNbOfPages().toString() + pages;
    }

    private void createSetupReminderClickListener(final View view, final int position) {
        ImageButton setupReminderButton = (ImageButton) view.findViewById(R.id.setup_reminder_button);
        setupReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Intent intent = new Intent(view.getContext(), SetupReminderActivity.class);
                intent.putExtra("book", books.get(position));
                view.getContext().startActivity(intent);
            }
        });
    }
}
