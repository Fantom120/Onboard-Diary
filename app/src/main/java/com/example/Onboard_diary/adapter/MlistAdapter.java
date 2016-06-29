package com.example.Onboard_diary.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.Onboard_diary.DataItem;
import com.example.Onboard_diary.MainActivity;
import com.example.Onboard_diary.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class MlistAdapter extends RecyclerView.Adapter<MlistAdapter.onBoardViewHolder> {
    private static List<DataItem> itemList;
    private static DataItem item;


    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("EE, dd MMMM yyyy", Locale.getDefault());


    public MlistAdapter(List<DataItem> data_item) {
        itemList = data_item;
    }

    @Override
    public onBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mitem, parent, false);

        return new onBoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(onBoardViewHolder holder, final int position) {
        item = itemList.get(position);

        holder.personImageView.setImageResource(R.mipmap.ic_pen);
        holder.nameTextView.setText(item.getTheme());
        holder.description.setText(item.getDescription());
        holder.date.setText(FORMATTER.format(item.getDate()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.onEditItem(itemList.get(position), "edit");
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }





    public static class onBoardViewHolder extends RecyclerView.ViewHolder {
        public onBoardViewHolder(final View itemView) {
            super(itemView);
        }
        final CardView cardView = (CardView) itemView.findViewById(R.id.cardView);
        final TextView nameTextView = (TextView) itemView.findViewById(R.id.textTheme);
        final  TextView description = (TextView) itemView.findViewById(R.id.textDescription);
        final  ImageView personImageView = (ImageView) itemView.findViewById(R.id.imageView);
        final TextView date = (TextView) itemView.findViewById(R.id.textDate);




    }


}
