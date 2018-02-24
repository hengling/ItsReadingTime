package com.ahengling.itsreadingtime;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ahengling.itsreadingtime.events.AlarmReminder;
import com.ahengling.itsreadingtime.helper.UiHelper;
import com.ahengling.itsreadingtime.model.Book;
import com.ahengling.itsreadingtime.ui.DatePickerFragment;
import com.ahengling.itsreadingtime.ui.TimePickerFragment;
import com.ahengling.itsreadingtime.util.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SetupReminderActivity extends AppCompatActivity {

    private Book book;

    @Override
    public void onBackPressed() {
        goToBooksListingActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_reminder);

        this.book = (Book) getIntent().getSerializableExtra("book");
        setBookTitleOnScreen();

        createSaveReminderButtonClickListener();
    }

    public void showDatePickerDialog(View v) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setEditText((EditText) findViewById(R.id.date_edit_text));
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
        UiHelper.hideKeyboard(v, this);
    }

    public void showTimePickerDialog(View v) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setEditText((EditText) findViewById(R.id.time_edit_text));
        timePickerFragment.show(getSupportFragmentManager(), "timePicker");
        UiHelper.hideKeyboard(v, this);
    }

    private void setBookTitleOnScreen() {
        TextView textView = (TextView) findViewById(R.id.book_title_text_view);
        textView.setText(book.getTitle());
    }

    private void createSaveReminderButtonClickListener() {
        Button setReminderButton = (Button) findViewById(R.id.btn_save_reminder);
        setReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent alarmReminderIntent = createAlarmReminderIntent();
                PendingIntent pendingIntent = createPendingIntentForAlarmReminder(alarmReminderIntent);

                if (!isDateValid() || !isTimeValid()) {
                    return;
                }

                try {
                    setupAlarmReminder(pendingIntent, getDateTimeInMillis());
                    showToastMessage(getString(R.string.msg_reminder_created));
                    goToBooksListingActivity();
                } catch (Exception e) {
                    e.printStackTrace();
                    showToastMessage(getString(R.string.msg_error_default));
                }
            }
        });
    }

    private Intent createAlarmReminderIntent() {
        Intent intent = new Intent(SetupReminderActivity.this, AlarmReminder.class);
        intent.putExtra("book", book);
        return intent;
    }

    private PendingIntent createPendingIntentForAlarmReminder(Intent alarmIntent) {
        return PendingIntent.getBroadcast(getApplicationContext(), 0,
                alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private Boolean isDateValid() {
        EditText dateEditText = (EditText) findViewById(R.id.date_edit_text);
        if (isEditTextEmpty(dateEditText)) {
            setErrorOnEditText(dateEditText, getResources().getString(R.string.msg_invalid_date));
            return false;
        }

        return isDateFormatValid(dateEditText);
    }

    private Boolean isDateFormatValid(EditText dateEditText) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DEFAULT.DATE_FORMAT);
        try {
            formatter.parse(dateEditText.getText().toString());
            return true;
        } catch (ParseException pe) {
            pe.printStackTrace();
            setErrorOnEditText(dateEditText, getResources().getString(R.string.msg_invalid_date));
            return false;
        }
    }

    private Boolean isTimeValid() {
        EditText timeEditText = (EditText) findViewById(R.id.time_edit_text);
        if (isEditTextEmpty(timeEditText)) {
            setErrorOnEditText(timeEditText, getResources().getString(R.string.msg_invalid_time));
            return false;
        }

        return isTimeTimeFormatValid(timeEditText);
    }

    private Boolean isTimeTimeFormatValid(EditText timeEditText) {
        String errorMessage = getResources().getString(R.string.msg_invalid_time);
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DEFAULT.TIME_FORMAT);
        try {
            formatter.parse(timeEditText.getText().toString());
            return true;
        } catch (ParseException pe) {
            pe.printStackTrace();
            setErrorOnEditText(timeEditText, errorMessage);
            return false;
        }
    }

    private Boolean isEditTextEmpty(EditText editText) {
        return editText.getText().toString().isEmpty();
    }

    private void setErrorOnEditText(EditText editText, String msg) {
        editText.setError(msg);
    }

    private Long getDateTimeInMillis() throws ParseException {
        EditText dateEditText = (EditText) findViewById(R.id.date_edit_text);
        EditText timeEditText = (EditText) findViewById(R.id.time_edit_text);

        String dateTimeStr = dateEditText.getText().toString() + " " + timeEditText.getText().toString();
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DEFAULT.DATETIME_FORMAT);

        Date date = formatter.parse(dateTimeStr);
        return date.getTime();
    }

    private void setupAlarmReminder(PendingIntent pendingIntent, Long triggerAtMillis) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
    }

    private void goToBooksListingActivity() {
        Intent intent = new Intent(SetupReminderActivity.this, BooksListingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void showToastMessage(String messsage) {
        Toast.makeText(this, messsage, Toast.LENGTH_LONG).show();
    }
}
