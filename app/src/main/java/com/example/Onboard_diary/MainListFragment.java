package com.example.Onboard_diary;


import android.os.AsyncTask;

import android.os.Bundle;


import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.sort;

public class MainListFragment extends ListFragment  {


    String LOG_TAG = "log";
   private  List<DataItem> data_itemList;
    private Db_Main db;
    private  AdapterMlist mAdapter;

    private MainActivity activity;
    private ListView mlistView;
    private View view;

    public MainListFragment(){
        this.setRetainInstance(true);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new Db_Main(getActivity());

        data_itemList = new ArrayList<>();

        loader.execute();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Проверяем, создано ли представление фрагмента
        if(view == null) {
            // Если представления нет, создаем его
            view = inflater.inflate(R.layout.mfragment, container, false);
        } else {
            // Если представление есть, удаляем его из разметки,
            // иначе возникнет ошибка при его добавлении
            ((ViewGroup) view.getParent()).removeView(view);
        }

        mlistView = (ListView) view.findViewById(android.R.id.list);


        if (getActivity() != null) {
            activity = (MainActivity) getActivity();
            setHasOptionsMenu(true);
            // data_itemList = db.getAllData();

        }



       mAdapter = new AdapterMlist(getActivity(), android.R.id.list, data_itemList);
        mlistView.setAdapter(mAdapter);

        mlistView.setLongClickable(true);
        //      Set<String> set = iconsMap.keySet();

        mlistView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return true;
            }
        });
        Log.d(LOG_TAG, "Oncreate View");

        mlistView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

         }

         @Override
         public void onNothingSelected(AdapterView<?> parent) {
             activity.mToolBar.setTitle(R.string.app_name);
             Log.d(LOG_TAG, "NothingSelected");

         }
     });

        return view;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // retrieve the item

            DataItem item = data_itemList.get(position);
            EditDataFragment edit = new EditDataFragment();
            Log.d(LOG_TAG, "onListItemClick");
            Bundle bundle = new Bundle();
            bundle.putParcelable("edit", item);

            edit.setArguments(bundle);
           activity.onEditItem(edit);


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.input_add:
                AddDataFragment addDataFragment = new AddDataFragment();
                activity.onAddItem(addDataFragment);

        }

        return super.onOptionsItemSelected(item);
    }


    AsyncTask<String, Integer, List<DataItem>> loader = new AsyncTask<String, Integer, List<DataItem>>() {

        @Override
        protected List<DataItem> doInBackground(String... params) {
            return db.getAllData();

        }

        @Override
        protected void onPostExecute(List<DataItem> items) {
            for (DataItem item : items) {
                data_itemList.add(item);

            }
       Collections.sort(data_itemList);
            mAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }




}
