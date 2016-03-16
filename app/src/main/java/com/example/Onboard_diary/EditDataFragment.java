package com.example.Onboard_diary;

import android.app.ActionBar;
import android.app.Activity;

import android.app.DatePickerDialog;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;


public class EditDataFragment extends Fragment {

    private EditText editTheme, editDiscription;
    private TextView editDate;
    private DataItem item;
    private Db_Main db;
    private MainActivity activity;
    private int day;
    private int month;
    private int year;
    boolean isAdd = false;
    static SimpleDateFormat FORMATTER = new SimpleDateFormat("EE, dd MMMM yyyy", Locale.getDefault());
    Calendar calendar = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.input, container, false);

        editTheme = (EditText) view.findViewById(R.id.editTheme);
        editDate = (TextView) view.findViewById(R.id.editDate);
        editDiscription = (EditText) view.findViewById(R.id.editDescription);

        if (getArguments() != null) {
            item = getArguments().getParcelable("edit");
             isAdd = false;
        }
          if(isAdd){
              year = calendar.get(Calendar.YEAR);
              month = calendar.get(Calendar.MONTH);
              day = calendar.get(Calendar.DAY_OF_MONTH);
              editDate.setText(FORMATTER.format(calendar.getTime()));
          } else{
              editDate.setText(FORMATTER.format(item.getDate()));
              editTheme.setText(item.getTheme());
              editDiscription.setText(item.getDescription());

              calendar.setTimeInMillis(item.getDate());
              year = calendar.get(Calendar.YEAR);
              month = calendar.get(Calendar.MONTH);
              day = calendar.get(Calendar.DAY_OF_MONTH);
          }

        setHasOptionsMenu(true);

        if (getActivity() != null) {
            activity = (MainActivity) getActivity();
            db = new Db_Main(activity);
        }

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();

            }
        });

        Log.d("log", "onCreateView in EditDataFragment");
        return view;

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        activity.getMenuInflater().inflate(R.menu.edit_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu_item) {
        switch (menu_item.getItemId()) {
            case R.id.editNote: {
                if (notEmpty(editTheme) && notEmpty(editDiscription)) {
                    item.setTheme(editTheme.getText().toString());
                    item.setDescription(editDiscription.getText().toString());
                    item.setDate(calendar.getTimeInMillis());
                    if (isAdd) {

                        new DownloadTask().execute(item);
                    } else {

                        new UpdateTask().execute(item);
                    }
                    activity.onItemCreated();
                } else {
                    if (editTheme.getText().length() == 0) {
                        editTheme.requestFocus();
                    }
                    if (editDiscription.getText().length() == 0) {
                        editDiscription.requestFocus();
                    }
                    Toast.makeText(activity, "Заполните поле", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            case R.id.deleteItem:{
                if(item != null) {
                    CustomChoiseDialog choise = new CustomChoiseDialog();
                    Bundle args = new Bundle();
                    args.putParcelable("delete", item);
                    choise.setArguments(args);
                    choise.show(activity.getSupportFragmentManager(), "Dialog");
                }
            }
        }

        return super.onOptionsItemSelected(menu_item);

    }

    private void showDatePicker() {

        DatePickerFragment date = new DatePickerFragment();

        Bundle args = new Bundle();

        args.putInt("year", year);
        args.putInt("month", month);
        args.putInt("day", day);
        // args.putString("date", item.getDate());
        date.setArguments(args);

        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(activity.getSupportFragmentManager(), "Date Picker");
    }

    OnDateSetListener ondate = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int newyear, int monthOfYear,
                              int dayOfMonth) {


//            Locale locale = new Locale("ru", "RU");
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", locale);

            calendar.set(newyear, monthOfYear, dayOfMonth, 13, 15);
            //  date = FORMATTER.format(calendar.getTime());
            editDate.setText(FORMATTER.format(calendar.getTime()));

            Log.d("log", "ondate");
            year = newyear;
            month = monthOfYear;
            day = dayOfMonth;

        }
    };




    class UpdateTask extends AsyncTask<DataItem, Void, Void> {

        @Override
        protected Void doInBackground(DataItem... params) {
            db.updateItem(item);
            return null;
        }
    }

    class DownloadTask extends AsyncTask<DataItem, Void, Void> {

        @Override
        protected Void doInBackground(DataItem... item) {
            if (item.length != 0) {
                db.addItem(item[0]);
            }

            return null;
        }
    }


    private boolean notEmpty(EditText etText) {
        return etText.getText().toString().trim().length() != 0;
    }
}
