package com.example.Onboard_diary;


import android.os.Bundle;


import android.support.design.widget.Snackbar;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.Onboard_diary.record_play_audio.AudioRecordFragment;
import com.software.shell.fab.ActionButton;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.sort;

public class MainListFragment extends ListFragment {


    String LOG_TAG = "log";
    private List<DataItem> data_itemList;
    private Db_Main db;

    private MainActivity activity;
    private View view;

    public MainListFragment() {
        this.setRetainInstance(true);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new Db_Main(getActivity());

        data_itemList = new ArrayList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                data_itemList = db.getAllData();

            }
        }).run();
        Collections.sort(data_itemList);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            view = inflater.inflate(R.layout.mfragment, container, false);

        if (getActivity() != null) {
            activity = (MainActivity) getActivity();
            setHasOptionsMenu(true);

        }

        AdapterMlist mAdapter = new AdapterMlist(getActivity(), android.R.id.list, data_itemList);
        ListView mlistView = (ListView) view.findViewById(android.R.id.list);
        mlistView.setAdapter(mAdapter);


        ActionButton fab = (ActionButton) view.findViewById(R.id.action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditDataFragment addDataFragment = new EditDataFragment();
                activity.onEditItem(addDataFragment);
            }
        });

        Log.d(LOG_TAG, "Oncreate View");
        mlistView.setLongClickable(true);
        mlistView.setFocusable(true);
        mlistView.setFocusableInTouchMode(true);


        mlistView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final DataItem item = data_itemList.get(position);
                Snackbar.make(view, "", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Удалить", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                CustomDeleteDialog choise = new CustomDeleteDialog();
                                Bundle args = new Bundle();
                                args.putParcelable("delete", item);
                                choise.setArguments(args);
                                choise.show(getChildFragmentManager(), "Dialog");

                            }
                        }).show();
                return true;
            }
        });


        return view;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // retrieve the mitem

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
        switch (item.getItemId()){
            case R.id.addAudio:
               AudioRecordFragment recordFragment = new AudioRecordFragment();
                recordFragment.show(getChildFragmentManager(), "Audio");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }


}
