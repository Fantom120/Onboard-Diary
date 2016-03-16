package com.example.Onboard_diary;


import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
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
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

import java.util.List;

import static java.util.Collections.sort;

public class MainListFragment extends ListFragment  {


    String LOG_TAG = "log";
    List<DataItem> data_itemList;
    private Db_Main db;
    AdapterMlist mAdapter;
    public static final int RESULT_OK = -1;
    int MAINACTIVITY_REQUEST_CODE = 101;
    int UPDATE_ITEM = 102;
    private static final String TAG = "AddItem";
    private MainActivity activity;
    private ListView mlistView;
    private MenuItem addMenuItem;
    boolean isLongClick = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new Db_Main(getActivity());

        data_itemList = new ArrayList<>();

        loader.execute();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.mfragment, container, false);
        mlistView = (ListView) view.findViewById(android.R.id.list);
//        Map<String, Drawable> iconsMap = new HashMap<String, Drawable>();
//        Resources resources = getResources();
//        iconsMap.put(getString(R.string.aim), resources.getDrawable(R.drawable.ic_launcher));
//
//        iconsMap.put(getString(R.string.aim), resources.getDrawable(R.drawable.ic_launcher));

        //  initialize the items list


        if (getActivity() != null) {
            activity = (MainActivity) getActivity();
            setHasOptionsMenu(true);
            // data_itemList = db.getAllData();

        }

        sort(data_itemList);
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
        Log.d(LOG_TAG, "Oncreate");

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
        addMenuItem = menu.findItem(R.id.input_add);




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.input_add:
                DataItem addItem = new DataItem();
                EditDataFragment edit = new EditDataFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("edit", addItem);
                edit.setArguments(bundle);

                activity.onEditItem(edit);

        }

        return super.onOptionsItemSelected(item);
    }


    AsyncTask<String, Integer, List<DataItem>> loader = new AsyncTask<String, Integer, List<DataItem>>() {

        @Override
        protected List<DataItem> doInBackground(String... params) {

            List<DataItem> items = db.getAllData();

            if (items.size() == 0) {
                for (String title : params) {
                    items.add(new DataItem(title));
                }
                db.addItems(items);
            }

            return items;
        }

        @Override
        protected void onPostExecute(List<DataItem> items) {
            for (DataItem item : items) {
                data_itemList.add(item);
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }




}
