package com.example.bookdiary.ui.search;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.ContentFrameLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookdiary.MainActivity;
import com.example.bookdiary.R;
import com.example.bookdiary.ui.home.HomeFragment;
import com.example.bookdiary.ui.home.HomeViewModel;

import java.util.List;

public class Items_Adapter extends RecyclerView.Adapter<Items_Adapter.MyViewHolder> {

    private Context mContext;
    private List<Items> itemList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView author;
        public ImageView image;
        public Button btn;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            author = (TextView) view.findViewById(R.id.author);
            image = (ImageView) view.findViewById(R.id.bookImage);
            btn = (Button) view.findViewById(R.id.readbtn);

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
        final Items item = itemList.get(position);
        holder.title.setText(item.getTitle());
        holder.author.setText(item.getAuthors());
        holder.image.setImageBitmap(item.getImage());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                HomeViewModel homeViewModel = new HomeViewModel();
                homeViewModel.setData(item.getTitle(), item.getAuthors(), item.getImage());
                HomeFragment homeFragment = new HomeFragment();
                Bundle info = new Bundle();
                info.putString("title", item.getTitle());
                info.putString("authors", item.getAuthors());
                info.putParcelable("image", item.getImage());
                homeFragment.setArguments(info);
                switchContent(R.id.fragment_container, homeFragment);
            }
        });

    }

    public void switchContent(int id, Fragment fragment) {
        if (mContext == null)
            return;
        if (mContext instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) mContext;
            Fragment frag = fragment;
            mainActivity.switchContent(id, frag);
        }

    }

//    UpdateHomeListener updateHomeListener;
//    public interface UpdateHomeListener
//    {
//        public void onChangeToHome(String title, String authors, Bitmap image);
//    }
//
//    @Override
//    public void onAttach(Context context) {
//
//    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}