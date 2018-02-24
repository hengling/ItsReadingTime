package com.ahengling.itsreadingtime.ui;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import com.ahengling.itsreadingtime.util.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by adolfohengling on 2/23/18.
 */

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private EditText editText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (isTimeSet()) {
            try {
               return createAndFillWithChoosenTime();
            } catch (ParseException pe) {
                pe.printStackTrace();

                editText.setText("");
                return createAndFillWithCurrentTime();
            }
        }
        return createAndFillWithCurrentTime();
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.editText.setText(getSelectedTimeAsString(hourOfDay, minute));
        this.editText.setError(null);
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    private String getSelectedTimeAsString(int hour, int minute) {
        Date date = new Date();
        date.setHours(hour);
        date.setMinutes(minute);

        return formatTime(date);
    }

    private String formatTime(Date date) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DEFAULT.TIME_FORMAT);
        return dateFormatter.format(date.getTime());
    }

    private Boolean isTimeSet() {
        return !editText.getText().toString().isEmpty();
    }

    private TimePickerDialog createAndFillWithCurrentTime() {
        final Calendar c = Calendar.getInstance();
        return new TimePickerDialog(getActivity(), this, c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE), DateFormat.is24HourFormat(getActivity()));
    }

    private TimePickerDialog createAndFillWithChoosenTime() throws ParseException {
        String timeStr = editText.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DEFAULT.TIME_FORMAT);
        Date time = dateFormat.parse(timeStr);

        final Calendar c = Calendar.getInstance();
        c.setTime(time);

        return new TimePickerDialog(getActivity(), this, c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE), DateFormat.is24HourFormat(getActivity()));
    }

}
