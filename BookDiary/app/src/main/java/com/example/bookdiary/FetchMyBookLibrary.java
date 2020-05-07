package com.example.bookdiary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class FetchMyBookLibrary extends AsyncTask<String, Void, String> {

    private String authToken;
    private Bitmap currentBookImage;
    private String type;
    private ArrayList<Bitmap> images;
    private ArrayList<String> imageUrl;
    private ArrayList<String> authors;

    public FetchMyBookLibrary(String t, String token)
    {
        authToken = token;
        images = new ArrayList<Bitmap>();
        imageUrl = new ArrayList<String>();
        authors = new ArrayList<>();
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

    public String getAuthor(int index) {return authors.get(index); }

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
        else if(type == "removeCurrentRead")
        {
            result = NetworkUtils.removeCurrentRead(authToken, strings[0]);
        }
        else if(type == "postHistoryBook")
        {
            result = NetworkUtils.postHistoryBook(authToken, strings[0]);
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {


    }
}
