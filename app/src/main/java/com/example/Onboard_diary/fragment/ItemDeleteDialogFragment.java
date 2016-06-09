package com.example.Onboard_diary.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import com.example.Onboard_diary.DataItem;
import com.example.Onboard_diary.Db_Main;
import com.example.Onboard_diary.MainActivity;
import com.example.Onboard_diary.R;
import com.example.Onboard_diary.fragment.MainListFragment;

public class ItemDeleteDialogFragment extends DialogFragment {
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
            item = getArguments().getParcelable("delete");
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.delete_item);  // заголовок
        builder.setIcon(R.mipmap.delete);
        builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        db.deleteItem(item);
                    }
                }).run();
                activity.onItemCreated(MainListFragment.getInstance());


            }
        });
        builder.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.setCancelable(true);

        return builder.create();

    }


}

