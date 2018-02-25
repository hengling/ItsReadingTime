package com.ahengling.itsreadingtime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ahengling.itsreadingtime.adapter.BooksListAdapter;
import com.ahengling.itsreadingtime.config.db.AppDatabase;
import com.ahengling.itsreadingtime.model.Book;
import com.ahengling.itsreadingtime.model.BookDao;

import java.util.List;

public class BooksListingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_listing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BooksListingActivity.this, AddBookActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void onResume() {
        super.onResume();
        refreshListing();
    }

    private void refreshListing() {
        new findAllBooks().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_books_listing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setEmptyListingMessageOnView() {
        TextView booksNotFoundTextView = (TextView) findViewById(R.id.books_not_found_text_view);
        booksNotFoundTextView.setVisibility(View.VISIBLE);
        booksNotFoundTextView.setText(getString(R.string.label_no_books_found));
    }

    private void hideEmptyListingMessage() {
        TextView booksNotFoundTextView = (TextView) findViewById(R.id.books_not_found_text_view);
        booksNotFoundTextView.setVisibility(View.INVISIBLE);
        booksNotFoundTextView.setText("");
    }

    private class findAllBooks extends AsyncTask<Void, Void, List<Book>> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startProgressDialog();
        }

        @Override
        protected List<Book> doInBackground(Void... voids) {
            BookDao bookDao = AppDatabase.getInstance(BooksListingActivity.this).bookDao();
            return bookDao.getAll();
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            stopProgressDialog();

            if (books.isEmpty()) {
                setEmptyListingMessageOnView();
            } else {
                hideEmptyListingMessage();
            }

            BooksListAdapter adapter = new BooksListAdapter(BooksListingActivity.this, books);
            ListView listView = (ListView) findViewById(R.id.books_listview);
            listView.setAdapter(adapter);
        }


        private void startProgressDialog() {
            progressDialog = new ProgressDialog(BooksListingActivity.this);
            progressDialog.setMessage(getDialogMessage());
            progressDialog.show();
        }

        private void stopProgressDialog() {
            progressDialog.dismiss();
        }

        private String getDialogMessage() {
            return BooksListingActivity.this.getResources().getString(R.string.msg_please_wait);
        }
    }
}
