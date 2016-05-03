package com.example.Onboard_diary;

import android.graphics.drawable.Drawable;

/**
 * Created by Ipro on 26.01.2016.
 */
public class ListViewItem {

    public  Drawable icon;       // the drawable for the ListView mitem ImageView
    public  String title;        // the text for the ListView mitem title
    public  String date;  // the text for the ListView mitem description
    int position;

    public ListViewItem(Drawable icon, String title, String date) {
        this.icon = icon;
        this.title = title;
        this.date = date;
    }

    public ListViewItem(int position) {
     this.position = position;
    }

    public ListViewItem(String title, String date) {
        this.title = title;
        this.date = date;
    }
}
