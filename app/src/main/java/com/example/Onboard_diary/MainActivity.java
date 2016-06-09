package com.example.Onboard_diary;



import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import com.example.Onboard_diary.fragment.EditDataFragment;
import com.example.Onboard_diary.fragment.MainListFragment;


public class MainActivity extends AppCompatActivity {
    String LOG_TAG = "log";
    private Toolbar mToolBar;
    private static   FragmentTransaction fragmentTransaction;
    private static FragmentManager mFragmentManager;

    private static final String FRAGMENT_EDIT_NAME = "fragment_edit";
    private static final String FRAGMENT_LIST_NAME = "fragment_main_list";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mToolBar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolBar);

        mFragmentManager = getSupportFragmentManager();
        EditDataFragment edit_fragment = (EditDataFragment) mFragmentManager.findFragmentByTag(FRAGMENT_EDIT_NAME);

        if (edit_fragment != null) {
            fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frafment_conteiner, edit_fragment).commit();
        }else {
            onItemCreated(MainListFragment.getInstance());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");

    }

   public  void onItemCreated(MainListFragment mainListFragment) {
        mToolBar.setTitle(R.string.app_name);
        fragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentTransaction.replace(R.id.frafment_conteiner, mainListFragment, FRAGMENT_LIST_NAME).commit();

    }

   public static void onEditItem(DataItem item, String request) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(request, item);

       fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.frafment_conteiner, EditDataFragment.getInstance(bundle), FRAGMENT_EDIT_NAME).addToBackStack("myStack").commit();


    }





    @Override
    public void onBackPressed() {
        if (mFragmentManager.getBackStackEntryCount() > 0) {
            mFragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
        mToolBar.setTitle(R.string.app_name);
        mToolBar.setSubtitle("");

    }


    public Toolbar getmToolBar() {
        return mToolBar;
}
}

