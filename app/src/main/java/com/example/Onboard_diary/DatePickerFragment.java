package com.example.Onboard_diary;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DatePickerFragment extends DialogFragment  {

    OnDateSetListener ondateSet;


    public void setCallBack(OnDateSetListener ondate) {
        ondateSet = ondate;
    }

    private int year, month, day;
    static final SimpleDateFormat FORMATTER = new SimpleDateFormat("EE, dd MMMM yyyy", Locale.getDefault());
    DataItem item;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //     Use the current date as the default date in the picker
        Calendar calendar = Calendar.getInstance();
        if (getArguments() != null) {
            year = getArguments().getInt("year");
            month = getArguments().getInt("month");
            day = getArguments().getInt("day");
        } else {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }


        Log.d("log", "onCreateDialog");

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        return FORMATTER.format(calendar.getTime());
    }

}
