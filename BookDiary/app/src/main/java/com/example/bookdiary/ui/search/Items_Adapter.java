package com.example.bookdiary.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bookdiary.R;

import java.util.List;

public class Items_Adapter extends RecyclerView.Adapter<Items_Adapter.MyViewHolder> {

    private Context mContext;
    private List<Items> itemList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
        }
    }


    public Items_Adapter(Context mContext, List<Items> listItems) {
        this.mContext = mContext;
        this.itemList = listItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Items item = itemList.get(position);
        holder.title.setText(item.getTitle());
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }
}