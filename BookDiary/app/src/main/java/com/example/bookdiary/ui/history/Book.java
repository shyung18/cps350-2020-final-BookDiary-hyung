package com.example.bookdiary.ui.history;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.ArrayMap;
import android.util.Log;

import com.akshaykale.swipetimeline.TimelineObject;
import com.akshaykale.swipetimeline.TimelineObjectClickListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class Book implements TimelineObject {
    long timestamp;
    String name, url;
    private String authors = "";
    private String bookId = "";

    Book(long time, String category, String url) {
        timestamp = time;
        name = category;
        this.url = url;
    }

    public void setBookId(String bookId)
    {
        this.bookId = bookId;
    }

    public String getBookId()
    {
        return bookId;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }
    @Override
    public String getTitle() {
        return name;
    }
    @Override
    public String getImageUrl() {
        return url;
    }

    public String getAuthors()
    {
        return authors;
    }

    public void setAuthors(JSONArray a)
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


}
