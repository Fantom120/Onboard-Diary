package com.example.Onboard_diary.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import com.example.Onboard_diary.DataItem;
import com.example.Onboard_diary.Db_Main;
import com.example.Onboard_diary.MainActivity;
import com.example.Onboard_diary.R;
import com.example.Onboard_diary.fragment.record_play_audio.AudioRecordFragment;
import com.example.Onboard_diary.fragment.record_play_audio.Record;

import java.util.ArrayList;


public class ItemDeleteDialogFragment extends DialogFragment {
    private Db_Main db;
    private DataItem item;
    private MainActivity activity;
    private ArrayList<Record> audioList;
    private static final String APP_PREFERENCES = "audioPath";




    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (getActivity() != null) {
            activity = (MainActivity) getActivity();
            db = new Db_Main(activity);
        }


        if (getArguments() != null) {
            item = getArguments().getParcelable("delete");
            audioList = getArguments().getParcelableArrayList("rec");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.delete_item);  // заголовок
        builder.setIcon(R.mipmap.delete);
        builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            if(item != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        db.deleteItem(item);
                    }
                }).run();
            }
                deleteAudioFiles(activity ,item);
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

 public void deleteAudioFiles(final Context ctx, final DataItem item){

     new Thread(new Runnable() {
         @Override
         public void run() {
             if(audioList != null && !audioList.isEmpty()) {
                 AudioRecordFragment recordFragment = new AudioRecordFragment();
                 for (int i = 0; i < audioList.size() ; i++) {
                     recordFragment.deleteAudioFile(audioList.get(i).getPathFile());
                 }
             }
             if(ctx != null) {
                 SharedPreferences pref = ctx.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
                 SharedPreferences.Editor editor = pref.edit();
                 editor.remove(item.getAudioPathKey());
                 editor.apply();
             }
         }
     }).run();

 }

}

