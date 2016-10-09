package com.example.Onboard_diary.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.Onboard_diary.DataItem;
import com.example.Onboard_diary.Db_Main;
import com.example.Onboard_diary.MainActivity;
import com.example.Onboard_diary.R;



public class ChoiseFragment extends DialogFragment {
    private Db_Main db;
    private DataItem item;
    private MainActivity activity;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (getActivity() != null) {
            activity = (MainActivity) getActivity();
            db = new Db_Main(activity);
        }
        if (getArguments() != null) {
            item = getArguments().getParcelable("save");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.save);
        builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener()  {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        db.addItem(item);
                    }
                }).run();
                activity.onItemCreated(MainListFragment.getInstance());

            }
        });

        return super.onCreateDialog(savedInstanceState);
    }
}
