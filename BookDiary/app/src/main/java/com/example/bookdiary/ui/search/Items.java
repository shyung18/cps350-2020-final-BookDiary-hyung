package com.example.bookdiary.ui.search;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.ArrayMap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class Items {
    private String title;
    private String authors = "";
    private Bitmap image;
    private String bookId;
    private String authToken;

    Items(String token) {authToken = token;}


    String getTitle()
    {
        return title;
    }

    String getAuthToken() {return authToken;}

    String getAuthors()
    {
        return authors;
    }

    Bitmap getImage()
    {
        return image;
    }

    String getBookId() {return bookId;}
    

    void setTitle(String t) {
        title = t;
    }

    void setAuthToken(String token) {authToken = token;}

    void setAuthors(JSONArray a)
    {
        for(int i =0; i<a.length(); i++)
        {
            try{
                authors+=a.get(i).toString() + ", ";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    void setImage(Bitmap bitmap)
    {
            image = bitmap;
    }

    void setBookId(String id)
    {
        bookId = id;
    }

}
