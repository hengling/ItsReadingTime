package com.ahengling.itsreadingtime.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ahengling.itsreadingtime.R;
import com.ahengling.itsreadingtime.SetupReminderActivity;
import com.ahengling.itsreadingtime.config.db.AppDatabase;
import com.ahengling.itsreadingtime.model.Book;
import com.ahengling.itsreadingtime.model.BookDao;
import com.ahengling.itsreadingtime.util.Constants;

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
        createDeleteBookClickListener(view, position);

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

    private void createDeleteBookClickListener(final View view, final int position) {
        ImageButton deleteBookButton = (ImageButton) view.findViewById(R.id.delete_book_button);
        deleteBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                createDeleteBookDialog(view.getContext(), books.get(position));
            }
        });
    }

    private void createDeleteBookDialog(final Context context, final Book book) {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.delete_book_dialog))
                .setMessage(createDeleteDialogMessage(context, book))
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton(context.getString(R.string.btn_ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                new deleteBook(context).execute(book);
                            }
                        })
                .setNegativeButton(context.getString(R.string.btn_cancel), null).show();
    }

    private String createDeleteDialogMessage(Context context, Book book) {
        StringBuilder message = new StringBuilder(context.getString(R.string.msg_confirm_delete_book))
                .append(" ").append(book.getTitle()).append("?");
        return message.toString();
    }

    private static void broadcastChanges(Context context) {
        Intent i = new Intent(Constants.EVENTS.BOOK.DELETED);
        context.sendBroadcast(i);
    }

    private static class deleteBook extends AsyncTask<Book, Void, Boolean> {

        private Context context;

        public deleteBook(Context context) {
            super();
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Book... books) {
            try {
                BookDao bookDao = AppDatabase.getInstance(context).bookDao();
                bookDao.delete(books[0]);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (!success) {
                String errorMessage = context.getString(R.string.msg_error_default);
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
                return;
            }
            broadcastChanges(context);
            Toast.makeText(context, context.getString(R.string.msg_book_deleted_successfully),
                    Toast.LENGTH_LONG).show();
        }
    }
}
