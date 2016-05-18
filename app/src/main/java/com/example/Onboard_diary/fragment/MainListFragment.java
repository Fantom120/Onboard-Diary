package com.example.Onboard_diary.fragment;


import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;

import com.example.Onboard_diary.*;
import com.example.Onboard_diary.adapter.MlistAdapter;
import com.example.Onboard_diary.record_play_audio.AudioRecordFragment;
import com.software.shell.fab.ActionButton;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;


public class MainListFragment extends  Fragment  {


    String LOG_TAG = "log";
    private List<DataItem> data_itemList;
    private Db_Main db;
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
            setHasOptionsMenu(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        MlistAdapter adapter = new MlistAdapter(data_itemList);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);



        ActionButton fab = (ActionButton) view.findViewById(R.id.action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              MainActivity.onEditItem(new DataItem(),"add");
            }
        });

        Log.d(LOG_TAG, "Oncreate View");
//        mlistView.setLongClickable(true);
//        mlistView.setFocusable(true);
//        mlistView.setFocusableInTouchMode(true);

//
//        mlistView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
//                final DataItem item = data_itemList.get(position);
//                Snackbar.make(view, "", Snackbar.LENGTH_INDEFINITE)
//                        .setAction("Удалить", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//                                ItemDeleteDialogFragment choise = new ItemDeleteDialogFragment();
//                                Bundle args = new Bundle();
//                                args.putParcelable("delete", item);
//                                choise.setArguments(args);
//                                choise.show(getChildFragmentManager(), "Dialog");
//
//                            }
//                        }).show();
//                return true;
//            }
//        });


        return view;
    }



//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        // retrieve the mitem
//
//        DataItem item = data_itemList.get(position);
//        EditDataFragment edit = new EditDataFragment();
//        Log.d(LOG_TAG, "onListItemClick");
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("edit", item);
//
//        edit.setArguments(bundle);
//        activity.onEditItem(edit);
//
//
//    }


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
