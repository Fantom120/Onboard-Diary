package com.example.Onboard_diary;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.app.DatePickerDialog.OnDateSetListener;
import com.example.Onboard_diary.record_play_audio.AudioPlayFragment;
import com.example.Onboard_diary.record_play_audio.AudioRecordFragment;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class EditDataFragment extends Fragment {

    private EditText editTheme, editDiscription;

    private DataItem item;
    private Db_Main db;
    private MainActivity activity;
    private int day;
    private int month;
    private int year;
    private boolean newItem;
    private Button btnRec, btnPlay;

    private static final SimpleDateFormat FORMAT_TITLE = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
    private static final SimpleDateFormat FORMAT_SUBTITLE = new SimpleDateFormat("EEEE", Locale.getDefault());
    private Calendar calendar = Calendar.getInstance();
    private View view;

    public EditDataFragment() {
        this.setRetainInstance(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.input, container, false);


        //   ((ViewGroup) view.getParent()).removeView(view);

        if (getActivity() != null) {
            activity = (MainActivity) getActivity();
            db = new Db_Main(activity);

        }

        btnRec = (Button) view.findViewById(R.id.btnRecord);
        btnRec.setOnClickListener(rec);

        btnPlay = (Button) view.findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(play);

        editTheme = (EditText) view.findViewById(R.id.editTheme);

        editDiscription = (EditText) view.findViewById(R.id.editDescription);


        if (getArguments() != null) {
            item = getArguments().getParcelable("edit");
            if (item != null) {


                editTheme.setText(item.getTheme());
                editDiscription.setText(item.getDescription());



                calendar.setTimeInMillis(item.getDate());
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                newItem = false;
            }
        } else {
            item = addNewItem();
            newItem = true;
        }

        if(item.getAudioPath() != null){
           btnPlay.setVisibility(View.VISIBLE);
        }
        setHasOptionsMenu(true);


//        editDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDatePicker();
//            }
//        });

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
                if (isEmpty(editTheme) && isEmpty(editDiscription)) break;
                else {
                    item.setTheme(editTheme.getText().toString());
                    item.setDescription(editDiscription.getText().toString());
                    item.setDate(calendar.getTimeInMillis());
                    if(newItem) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                db.addItem(item);
                            }
                        }).run();

                        Log.d("log", item.getDescription());
                    }
                    else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                db.updateItem(item);
                            }
                        }).run();

                    }

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


            Log.d("log", "ondate");
            year = newyear;
            month = monthOfYear;
            day = dayOfMonth;

        }
    };


    public void setAudioPath(String path) {
        if (path != null) {
            item.setAudioPath(path);
        }
    }


    private DataItem addNewItem() {
        DataItem add_item = new DataItem();
        add_item.setDate(calendar.getTimeInMillis());
        return add_item;
    }

    private boolean isEmpty(EditText etText) {
        if (etText != null && etText.getText().toString().trim().length() == 0) {
            etText.requestFocus();
            Toast.makeText(getActivity(), R.string.isEmpty, Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }

    }

    View.OnClickListener rec = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AudioRecordFragment recordFragment = new AudioRecordFragment();
           recordFragment.show(getActivity().getSupportFragmentManager(), "AudioRec");
        }
    };

    View.OnClickListener play  = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AudioPlayFragment playFragment = new AudioPlayFragment();
            Bundle args = new Bundle();
            args.putString("path", item.getAudioPath());
            playFragment.setArguments(args);
            playFragment.show(getActivity().getSupportFragmentManager(), "PlayAudio");
        }
    };


}
