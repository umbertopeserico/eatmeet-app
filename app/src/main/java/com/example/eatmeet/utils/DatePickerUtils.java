package com.example.eatmeet.utils;

import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by umberto on 21/08/16.
 */
public class DatePickerUtils {

    public static Date getDateFromDatePicker(DatePicker dp) {
        Calendar cal = Calendar.getInstance();
        int year = dp.getYear();
        int month = dp.getMonth();
        int day = dp.getDayOfMonth();
        cal.set(year, month, day);
        return cal.getTime();
    }
}
