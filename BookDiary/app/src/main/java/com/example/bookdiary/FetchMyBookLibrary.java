package com.example.bookdiary;

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
import com.example.bookdiary.ui.search.NetworkUtils;

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

public class FetchMyBookLibrary extends AsyncTask<String, Void, String> {

    private String authToken;
    private Bitmap currentBookImage;
    private String type;
    private ArrayList<Bitmap> images;
    private ArrayList<String> imageUrl;

    public FetchMyBookLibrary(String t, String token)
    {
        authToken = token;
        images = new ArrayList<Bitmap>();
        imageUrl = new ArrayList<String>();
        type = t;
    }

    public Bitmap getCurrentImage()
    {
        return currentBookImage;
    }

    public Bitmap getImages(int index)
    {
        return images.get(index);
    }

    public String getImageUrl(int index) {return imageUrl.get(index); }

    @Override
    protected String doInBackground(String... strings) {
        //String data = NetworkUtils.getBookInfo(strings[0]);
        String result = "";
        if(type == "getCurrentRead")
        {
            result = NetworkUtils.getCurrentRead(authToken);

            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray itemsArray = jsonObject.getJSONArray("items");

                JSONObject book = itemsArray.getJSONObject(0);
                String imageUrl = null;
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    imageUrl= volumeInfo.getJSONObject("imageLinks").getString("thumbnail");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (imageUrl!=null) {
                    try {
                        currentBookImage = (BitmapFactory.decodeStream((InputStream)new URL(imageUrl).getContent()));
                    } catch (Exception e) {
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(type == "postCurrentRead")
        {
            result = NetworkUtils.postCurrentRead(authToken, strings[0]);
        }
        else if(type == "getSearchResult")
        {
            result = NetworkUtils.getBookInfo(strings[0]);

            try {
                JSONObject jsonObject = new JSONObject(result);
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
        }
        else if(type == "getPastReadBooks")
        {
            result = NetworkUtils.getReadBooks(authToken);
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray itemsArray = jsonObject.getJSONArray("items");
                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject book = itemsArray.getJSONObject(i);
                    String imgUrl = null;
                    JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                    try {
                        imgUrl= volumeInfo.getJSONObject("imageLinks").getString("thumbnail");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (imgUrl!=null) {
                        imageUrl.add(imgUrl);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {


    }
}
