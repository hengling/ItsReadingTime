package com.ahengling.itsreadingtime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ahengling.itsreadingtime.model.Book;

public class SetupReminderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_reminder);

        Book book = (Book) getIntent().getSerializableExtra("book");

        TextView textView = (TextView) findViewById(R.id.book_title_text_view);
        textView.setText(book.getTitle());
    }
}
