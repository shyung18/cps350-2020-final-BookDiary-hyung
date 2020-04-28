package com.example.bookdiary.ui.search;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bookdiary.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchBook extends AsyncTask<String, Void, String> {

    private CardView dataList;
    private ArrayList<String> titles;
    private ArrayList<String> authors;
    private ArrayList<Bitmap> images;

    FetchBook()
    {
        dataList = new CardView();
        titles = new ArrayList<String>();
        authors = new ArrayList<String>();
        images = new ArrayList<Bitmap>();
    }

    Bitmap getImages(int index)
    {
        return images.get(index);
    }

    @Override
    protected String doInBackground(String... strings) {
        String data = NetworkUtils.getBookInfo(strings[0]);
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject book = itemsArray.getJSONObject(i);
                String imageUrl = null;
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    imageUrl= volumeInfo.getJSONObject("imageLinks").getString("thumbnail");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (imageUrl!=null) {
                    try {
                        images.add(BitmapFactory.decodeStream((InputStream)new URL(imageUrl).getContent()));
                    } catch (Exception e) {
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result) {


    }
}
