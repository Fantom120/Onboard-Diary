package com.example.Onboard_diary;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class AdapterMlist extends ArrayAdapter<DataItem> {

 //   LayoutInflater inflater;
    private List<DataItem> itemList;
    private Map<String, Drawable> mIconsMap;
    DataItem dataItem;
    Context context;
    private int day;
    private int month;
    private int year;
    static SimpleDateFormat FORMATTER = new SimpleDateFormat("EE, dd MMMM yyyy", Locale.getDefault());
    private Calendar calendar = Calendar.getInstance();

    public AdapterMlist(Context context, List<DataItem> itemList) {
        super(context,R.layout.item);

    //    this.itemList = itemList;
     //   mIconsMap = iconsMap;
    }

    public AdapterMlist(Context context, int id, List<DataItem> data_itemList) {
        super(context, id,data_itemList);
        this.context = context;
        this.itemList = data_itemList;
    }


    @Override
    public int getCount() { return itemList.size();}

//    @Override
//    public ListViewItem getItem(int position) {
//
//      DataItem item = itemList.get(position);
//        String title = item.getTheme();
//        return new ListViewItem(mIconsMap.get(title),title,getContext()
//                .getString(R.string.number_of_clicks, item.getNumClicks()));
//
//    }
//  @Override
//    public ListViewItem getItem(int position) {
//      DataItem item = itemList.get(position);
//      String title = item.getTheme();
//      String date = item.getDate();
//      return new ListViewItem(title,date);
//
//    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
       // dataItem = new DataItem();
        dataItem = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
           // LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = mInflater.inflate(R.layout.item,parent,false);

            viewHolder = new ViewHolder();
            viewHolder.personImageView = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.nameTextView =(TextView) convertView.findViewById(R.id.textTheme);
            viewHolder.date =(TextView) convertView.findViewById(R.id.textDate);
            convertView.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();}
          //  ListViewItem item = getItem(position);
         //   viewHolder.personImageView.setImageDrawable(item.icon);
            viewHolder.personImageView.setImageResource(R.drawable.ic_launcher);
            viewHolder.nameTextView.setText(dataItem.getTheme());
            viewHolder.date.setText(FORMATTER.format(dataItem.getDate()));


        return convertView;
    }


    // DataItem getItem_Data(int position) {return (DataItem) getItem(position); }
}
