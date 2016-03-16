package com.example.Onboard_diary;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;



public class MainActivity extends AppCompatActivity  {
    String LOG_TAG = "log";

    FragmentTransaction fragmentTransaction;
    private FragmentManager mFragmentManager;
    Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mToolBar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolBar);

        mFragmentManager = getSupportFragmentManager();

        //    mFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if(fragmentTransaction == null) {
            fragmentTransaction = mFragmentManager.beginTransaction();

             mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            fragmentTransaction.add(R.id.frafment_conteiner, new MainListFragment());
     fragmentTransaction.commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");

    }


    public void onItemCreated() {
        mToolBar.setTitle(R.string.app_name);
        fragmentTransaction = mFragmentManager.beginTransaction();

        mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentTransaction.replace(R.id.frafment_conteiner, new MainListFragment());
        fragmentTransaction.commit();

    }

    public void onEditItem(EditDataFragment edit) {
        mToolBar.setTitle(R.string.note);
        fragmentTransaction = mFragmentManager.beginTransaction();
        //  fragmentTransaction.setCustomAnimations(R.animator.slide_in_left,R.animator.slide_in_right);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.frafment_conteiner, edit).addToBackStack("myStack");
        fragmentTransaction.commit();

    }


    @Override
    public void onBackPressed() {
        if (mFragmentManager.getBackStackEntryCount() > 0) {
            mFragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
        mToolBar.setTitle(R.string.app_name);
    }




}

