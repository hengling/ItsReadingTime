package com.ahengling.itsreadingtime.helper;

import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by adolfohengling on 2/22/18.
 */

public class UiHelper {

    public static void hideKeyboard(View view, Context context) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void vibrateFor(Context context, int millis) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(millis);
    }
}
