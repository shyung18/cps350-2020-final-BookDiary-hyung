package com.example.bookdiary.ui.history;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bookdiary.MainActivity;
import com.example.bookdiary.R;
import com.example.bookdiary.ui.home.ReflectionView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class BookUpdate extends Activity {
    private Context mContext;
    private FragmentTransaction ft;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_current);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        mContext = getApplicationContext();

        getWindow().setLayout((int)(width*.9),(int)(height*.9));

        Intent i = getIntent();
        String title = i.getStringExtra("title");
        String authors = i.getStringExtra("authors");
        String imageUrl = i.getStringExtra("imageUrl");
        ImageView imageView = findViewById(R.id.main_image);
        TextView titleView = findViewById(R.id.main_title);
        TextView authorView = findViewById(R.id.main_author);
        Button button = findViewById(R.id.doneButton);
        ReflectionView reflectionView = findViewById(R.id.rView);
        reflectionView.setmText("NONE");
        button.setVisibility(View.GONE);
        titleView.setText(title);
        authorView.setText(authors);
        Bitmap bitmap = null;
        try {
            bitmap = (BitmapFactory.decodeStream(((InputStream) new URL(imageUrl).getContent())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bitmap);


    }

    public void switchContent(int id, androidx.fragment.app.Fragment fragment, Bundle bundle) {
        if (mContext == null)
            return;
        if (mContext instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) mContext;
            Fragment frag = fragment;
            mainActivity.switchContent(id, frag, bundle);
        }

    }

}
