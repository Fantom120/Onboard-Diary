package com.example.Onboard_diary;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.app.DatePickerDialog.OnDateSetListener;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class EditDataFragment extends Fragment {

    private EditText editTheme, editDiscription;
    private TextView editDate;
    private DataItem item;
    private Db_Main db;
    private MainActivity activity;
    private int day;
    private int month;
    private int year;

    private static final SimpleDateFormat FORMAT_TITLE = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
    private static final SimpleDateFormat FORMAT_SUBTITLE = new SimpleDateFormat("EEEE", Locale.getDefault());
    private Calendar calendar = Calendar.getInstance();
    private View view;

    public EditDataFragment() {
        this.setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


           view = inflater.inflate(R.layout.input, container, false);


            ((ViewGroup) view.getParent()).removeView(view);

        if (getActivity() != null) {
            activity = (MainActivity) getActivity();
            db = new Db_Main(activity);

        }

        editTheme = (EditText) view.findViewById(R.id.editTheme);
        editDate = (TextView) view.findViewById(R.id.editDate);

        editDiscription = (EditText) view.findViewById(R.id.editDescription);
        editDiscription.addTextChangedListener(watcher);

        if (getArguments() != null) {
            item = getArguments().getParcelable("edit");
            if (item != null) {

                editDate.setText(FORMAT_TITLE.format(item.getDate()));
                editTheme.setText(item.getTheme());
                editDiscription.setText(item.getDescription());

                calendar.setTimeInMillis(item.getDate());
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
            }
        }
        setHasOptionsMenu(true);


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
    public void onResume() {
        super.onResume();
        activity.getmToolBar().setTitle(FORMAT_TITLE.format(item.getDate()));
        activity.getmToolBar().setSubtitle(FORMAT_SUBTITLE.format(item.getDate()));
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
                if (activity.isEmpty(editTheme)) {
                    editTheme.requestFocus();
                    Toast.makeText(activity, "Заполните поле", Toast.LENGTH_SHORT).show();
                }
                if (activity.isEmpty(editDiscription)) {
                    editDiscription.requestFocus();
                    Toast.makeText(activity, "Заполните поле", Toast.LENGTH_SHORT).show();
                } else {
                    item.setTheme(editTheme.getText().toString());
                    item.setDescription(editDiscription.getText().toString());
                    item.setDate(calendar.getTimeInMillis());

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            db.updateItem(item);
                        }
                    }).run();

                    Log.d("log", item.getDescription());


                    activity.onItemCreated(new MainListFragment());
                }
                break;
            }
            case R.id.deleteItem: {
                if (item != null) {
                    CustomDeleteDialog choise = new CustomDeleteDialog();
                    Bundle args = new Bundle();
                    args.putParcelable("delete", item);
                    choise.setArguments(args);
                    choise.show(getChildFragmentManager(), "Dialog");
                }
                break;
            }

            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, item.getDescription());
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));

                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(menu_item);

    }

    private void showDatePicker() {

        DatePickerFragment date = new DatePickerFragment();

        Bundle args = new Bundle();

        args.putInt("year", year);
        args.putInt("month", month);
        args.putInt("day", day);
        date.setArguments(args);

        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(activity.getSupportFragmentManager(), "Date Picker");
    }

    private OnDateSetListener ondate = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int newyear, int monthOfYear,
                              int dayOfMonth) {

            calendar.set(newyear, monthOfYear, dayOfMonth, 13, 15);
            editDate.setText(FORMAT_TITLE.format(calendar.getTime()));

            Log.d("log", "ondate");
            year = newyear;
            month = monthOfYear;
            day = dayOfMonth;

        }
    };

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void setItem() {
        item.setTheme(editTheme.getText().toString());
        item.setDescription(editDiscription.getText().toString());
        item.setDate(calendar.getTimeInMillis());

    }


}
