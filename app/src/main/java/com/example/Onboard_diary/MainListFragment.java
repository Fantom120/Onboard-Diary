package com.example.Onboard_diary;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;


import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainListFragment extends ListFragment {


    String LOG_TAG = "log";
  List<DataItem> data_itemList;
   private Db_Main db;
    AdapterMlist  mAdapter;
    public static final int RESULT_OK  = -1;
    int MAINACTIVITY_REQUEST_CODE =  101;
    int UPDATE_ITEM = 102;
    private int position_item = 0;
    private static final String TAG = "AddItem";
    private MainActivity activity;
    FragmentTransaction fragmentTransaction;
    private FragmentManager mFragmentManager;
    private ListView mlistView;




//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        final ActionBar actionBar = activity.getActionBar();
//
//        actionBar.setTitle(R.string.app_name);
//        setHasOptionsMenu(true);
//
//    }
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    db = new Db_Main(getActivity());

    data_itemList = new ArrayList<DataItem>();

    loader.execute();

//     getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//         @Override
//         public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//             db.deleteItem(data_itemList.get(position));
//             data_itemList.remove(position);
//             mAdapter.notifyDataSetChanged();
//             return true;
//         }
//     });
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

        if (getActivity() != null)
        {
            activity = (MainActivity ) getActivity();
           setHasOptionsMenu(true);
           // data_itemList = db.getAllData();

        }

        mAdapter = new AdapterMlist(getActivity(),android.R.id.list,data_itemList);
        // setListAdapter(mAdapter);
        mlistView.setAdapter(mAdapter);


        //      Set<String> set = iconsMap.keySet();

     //   Collections.sort(data_itemList);

        Log.d(LOG_TAG, "Oncreate");

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.input_add:
                DataItem addItem = new DataItem();
                EditDataFragment edit  = new EditDataFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("edit", addItem);
                edit.setArguments(bundle);

                activity.onEditItem(edit);

        }

        return super.onOptionsItemSelected(item);
    }


    AsyncTask<String,Integer,List<DataItem>> loader = new AsyncTask<String, Integer, List<DataItem>>()
    {

        @Override
        protected List<DataItem> doInBackground(String... params) {

            List<DataItem> items = db.getAllData();

            if(items.size() == 0){
                for(String title : params){
                    items.add(new DataItem(title));
                }
                db.addItems(items);
            }

            return items;
        }

        @Override
        protected void onPostExecute(List<DataItem> items) {
            for(DataItem item: items){
                data_itemList.add(item);
                mAdapter.notifyDataSetChanged();
            }
        }
    };
//    @Override
//    public void onViewCreated(final View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//      //  getListView().setDivider(null);
//        Log.d(LOG_TAG, "onViewCreated");
//    }

    @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            // retrieve the item
            DataItem item = data_itemList.get(position);
        EditDataFragment edit  = new EditDataFragment();

        position_item = position;

        Bundle bundle = new Bundle();
        bundle.putParcelable("edit", item);
          edit.setArguments(bundle);

       activity.onEditItem(edit);
    }



    @Override
    public void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }



}
