package com.ahengling.itsreadingtime;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ahengling.itsreadingtime.config.db.AppDatabase;
import com.ahengling.itsreadingtime.helper.UiHelper;
import com.ahengling.itsreadingtime.model.Book;
import com.ahengling.itsreadingtime.model.BookDao;

public class AddBookActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        createOnSaveButtonClickListener();
    }

    private void createOnSaveButtonClickListener() {
        final Button saveButton = (Button) findViewById(R.id.save_book_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText bookTitleEditText = (EditText) findViewById(R.id.book_title_edit_text);
                final EditText bookPagesEditText = (EditText) findViewById(R.id.book_pages_edit_text);

                if (!isFormValid()) {
                    return;
                }

                Book book = new Book();
                book.setTitle(bookTitleEditText.getText().toString());
                book.setNbOfPages(Integer.parseInt(bookPagesEditText.getText().toString()));

                new saveBookTask().execute(book);
            }
        });
    }

    private Boolean isFormValid() {
        return isBookTitleValid() && isBookPagesValid();
    }

    private Boolean isBookTitleValid() {
        final EditText bookTitleEditText = (EditText) findViewById(R.id.book_title_edit_text);
        return isRequiredFieldFilled(bookTitleEditText, R.string.msg_book_title_required);
    }

    private Boolean isBookPagesValid() {
        final EditText bookPagesEditText = (EditText) findViewById(R.id.book_pages_edit_text);
        return isRequiredFieldFilled(bookPagesEditText, R.string.msg_book_pages_required);
    }

    private Boolean isRequiredFieldFilled(EditText editText, int idErrorMessage) {
        final EditText bookTitleEditText = (EditText) findViewById(R.id.book_title_edit_text);
        if (TextUtils.isEmpty(editText.getText())) {
            editText.setError(getResources().getString(idErrorMessage));
            return false;
        }
        return true;
    }

    private class saveBookTask extends AsyncTask<Book, Void, Boolean> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startProgressDialog();
        }

        @Override
        protected Boolean doInBackground(Book... books) {
            try {
                BookDao bookDao = AppDatabase.getInstance(AddBookActivity.this).bookDao();
                bookDao.insertAll(books);
                return true;
            } catch (Exception e) {
                Log.e("AddBookActivity", e.getMessage());
                return false;
            }
        }

        @Override
        public void onPostExecute(Boolean success) {
            stopProgressDialog();
            Context context = AddBookActivity.this;

            UiHelper.hideKeyboard(getCurrentFocus(), context);

            if (success) {
                String successMsg = context.getResources().getString(R.string.msg_add_book_success);
                Toast.makeText(AddBookActivity.this, successMsg, Toast.LENGTH_LONG).show();
                goToBookListingActivity();
                finish();
            } else {
                String errorMsg = context.getResources().getString(R.string.msg_error_default);
                Toast.makeText(AddBookActivity.this, errorMsg, Toast.LENGTH_LONG).show();
            }
        }

        private void startProgressDialog() {
            progressDialog = new ProgressDialog(AddBookActivity.this);
            progressDialog.setMessage(getDialogMessage());
            progressDialog.show();
        }

        private void stopProgressDialog() {
            progressDialog.dismiss();
        }

        private String getDialogMessage() {
            return AddBookActivity.this.getResources().getString(R.string.msg_please_wait);
        }

        private void goToBookListingActivity() {
            Intent intent = new Intent(AddBookActivity.this, BooksListingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
