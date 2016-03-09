package com.example.Onboard_diary;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;

import java.util.Calendar;

/**
 * Created by Ipro on 20.02.2016.
 */
public  class DatePickerFragment extends DialogFragment  {

    OnDateSetListener ondateSet;


    public void setCallBack(OnDateSetListener ondate) {
        ondateSet = ondate;
    }
    private int year, month, day;
//    @Override
//    public void setArguments(Bundle args) {
//        super.setArguments(args);
//
//        year = args.getInt("year");
//        month = args.getInt("month");
//        day = args.getInt("day");
//    }


   @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       //     Use the current date as the default date in the picker
       if(getArguments() != null){
           year = getArguments().getInt("year");
          month = getArguments().getInt("month");
          day = getArguments().getInt("day");
       }

       Log.d("log", "onCreateDialog");

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
    }

}
