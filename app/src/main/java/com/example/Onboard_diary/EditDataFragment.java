package com.example.Onboard_diary;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.TextView;
import android.widget.Toast;

import com.vk.sdk.VKSdk;
import com.vk.sdk.dialogs.VKShareDialogBuilder;

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

    static SimpleDateFormat FORMATTER = new SimpleDateFormat("EE, dd MMMM yyyy", Locale.getDefault());
    Calendar calendar = Calendar.getInstance();
    private View view;

   public EditDataFragment(){
       this.setRetainInstance(true);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Проверяем, создано ли представление фрагмента
        if(view == null) {
            // Если представления нет, создаем его
            view = inflater.inflate(R.layout.input, container, false);
        } else {
   //          Если представление есть, удаляем его из разметки,
     //        иначе возникнет ошибка при его добавлении
            ((ViewGroup) view.getParent()).removeView(view);
        }

        if (getActivity() != null) {
            activity = (MainActivity) getActivity();
            db = new Db_Main(activity);

        }


        editTheme = (EditText) view.findViewById(R.id.editTheme);
        editDate = (TextView) view.findViewById(R.id.editDate);



        editDiscription = (EditText) view.findViewById(R.id.editDescription);
        editDiscription.addTextChangedListener(watcher);


           if(getArguments()!= null) {
               item = getArguments().getParcelable("edit");
                if(item != null) {


                    editDate.setText(FORMATTER.format(item.getDate()));
                    editTheme.setText(item.getTheme());
                    editDiscription.setText(item.getDescription());

                    calendar.setTimeInMillis(item.getDate());
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                }
           }
        setHasOptionsMenu(true);
        activity.mToolBar.setTitle(FORMATTER.format(item.getDate()));



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
                    activity.count.setText("");

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
                if (activity.isEmpty(editDiscription)) {
                    editDiscription.requestFocus();
                    Toast.makeText(activity, "Заполните поле", Toast.LENGTH_SHORT).show();
                }
                setItem();
              ShareDialog shareDialog = new ShareDialog();
                Bundle args = new Bundle();
                args.putParcelable("share", item);
                shareDialog.setArguments(args);
                shareDialog.show(activity.getSupportFragmentManager(), "Share");

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

    OnDateSetListener ondate = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int newyear, int monthOfYear,
                              int dayOfMonth) {

            calendar.set(newyear, monthOfYear, dayOfMonth, 13, 15);
            editDate.setText(FORMATTER.format(calendar.getTime()));

            Log.d("log", "ondate");
            year = newyear;
            month = monthOfYear;
            day = dayOfMonth;

        }
    };

    TextWatcher watcher =  new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            activity.count.setText(String.valueOf(s.length()));


        }
    };

    private void setItem() {
        item.setTheme(editTheme.getText().toString());
        item.setDescription(editDiscription.getText().toString());
        item.setDate(calendar.getTimeInMillis());

    }


}
