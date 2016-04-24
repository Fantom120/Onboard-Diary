package com.example.Onboard_diary;



import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    String LOG_TAG = "log";

    private FragmentTransaction fragmentTransaction;
    private FragmentManager mFragmentManager;



    private Toolbar mToolBar;
    private static final String FRAGMENT_EDIT_NAME = "fragment_edit";
    private static final String FRAGMENT_ADD_NAME = "fragment_add";
    private static final String FRAGMENT_LIST_NAME = "fragment_main_list";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mToolBar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolBar);




        mFragmentManager = getSupportFragmentManager();

        EditDataFragment edit_fragment = (EditDataFragment) mFragmentManager.findFragmentByTag(FRAGMENT_EDIT_NAME);
        AddDataFragment add_fragment = (AddDataFragment) mFragmentManager.findFragmentByTag(FRAGMENT_ADD_NAME);

        if (edit_fragment != null) {
            fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frafment_conteiner, edit_fragment).commit();
        } else if (add_fragment != null) {
            fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frafment_conteiner, add_fragment).commit();
        } else {
            onItemCreated(new MainListFragment());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");

    }

    public void onItemCreated(MainListFragment mainListFragment) {
        mToolBar.setTitle(R.string.app_name);
        fragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentTransaction.replace(R.id.frafment_conteiner, mainListFragment, FRAGMENT_LIST_NAME).commit();

    }

    public void onEditItem(EditDataFragment edit) {

        fragmentTransaction = mFragmentManager.beginTransaction();
        //  fragmentTransaction.setCustomAnimations(R.animator.slide_in_left,R.animator.slide_in_right);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.frafment_conteiner, edit, FRAGMENT_EDIT_NAME).addToBackStack("myStack").commit();


    }

    public void onAddItem(AddDataFragment add_fragment) {
        fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.frafment_conteiner, add_fragment, FRAGMENT_ADD_NAME).addToBackStack("myStack").commit();

    }



    public boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
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

