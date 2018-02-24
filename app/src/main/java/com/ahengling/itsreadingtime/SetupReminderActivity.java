package com.ahengling.itsreadingtime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ahengling.itsreadingtime.model.Book;
import com.ahengling.itsreadingtime.ui.DatePickerFragment;
import com.ahengling.itsreadingtime.ui.TimePickerFragment;

public class SetupReminderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_reminder);

        Book book = (Book) getIntent().getSerializableExtra("book");
        setBookTitleOnScreen(book);
    }

    public void showDatePickerDialog(View v) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setEditText((EditText) findViewById(R.id.date_edit_text));
        datePickerFragment.setFormat("dd/MM/yyyy");
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setEditText((EditText) findViewById(R.id.time_edit_text));
        timePickerFragment.show(getSupportFragmentManager(), "timePicker");
    }

    private void setBookTitleOnScreen(Book book) {
        TextView textView = (TextView) findViewById(R.id.book_title_text_view);
        textView.setText(book.getTitle());
    }
}
