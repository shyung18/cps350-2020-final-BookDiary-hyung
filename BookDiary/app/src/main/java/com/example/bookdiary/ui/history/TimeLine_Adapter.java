package com.example.bookdiary.ui.history;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookdiary.MainActivity;
import com.example.bookdiary.R;
import com.example.bookdiary.ui.home.HomeFragment;
import com.example.bookdiary.ui.home.HomeViewModel;
import com.example.bookdiary.ui.search.Items;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

public class TimeLine_Adapter extends RecyclerView.Adapter<TimeLineViewHolder> {

    private Context mContext;
    private List<Items> itemList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MyViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_timeline, null);
        return new TimeLineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(final TimeLineViewHolder holder, int position) {
        //holder.mTimelineView.set

    }

    public void switchContent(int id, Fragment fragment) {
        if (mContext == null)
            return;
        if (mContext instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) mContext;
            Fragment frag = fragment;
            mainActivity.switchContent(id, frag, null);
        }

    }

}