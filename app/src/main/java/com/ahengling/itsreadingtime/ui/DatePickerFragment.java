package com.ahengling.itsreadingtime.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import com.ahengling.itsreadingtime.util.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by adolfohengling on 2/23/18.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private EditText editText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (isDateSet()) {
            try {
                return createAndfillDialogWithChoosenDate();
            } catch (ParseException pe) {
                pe.printStackTrace();

                editText.setText("");
                return createAndFillDigalogWithDefaultDate();
            }
        }
        return createAndFillDigalogWithDefaultDate();
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        editText.setText(formatDate(year, month, day));
        this.editText.setError(null);
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    private String formatDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DEFAULT.DATE_FORMAT);
        return dateFormatter.format(calendar.getTime());
    }

    private Boolean isDateSet() {
        return !editText.getText().toString().isEmpty();
    }

    private DatePickerDialog createAndFillDigalogWithDefaultDate() {
        final Calendar c = Calendar.getInstance();
        return new DatePickerDialog(getActivity(), this, c.get(Calendar.YEAR),
                c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    }

    private DatePickerDialog createAndfillDialogWithChoosenDate() throws ParseException {
        String dateStr = editText.getText().toString();
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DEFAULT.DATE_FORMAT);
        Date choosenDate = formatter.parse(dateStr);
        Calendar c = Calendar.getInstance();
        c.setTime(choosenDate);

        return new DatePickerDialog(getActivity(), this, c.get(Calendar.YEAR),
                c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    }



}
