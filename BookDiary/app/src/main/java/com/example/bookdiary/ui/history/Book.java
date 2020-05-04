package com.example.bookdiary.ui.history;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.ArrayMap;
import android.util.Log;

import com.akshaykale.swipetimeline.TimelineObject;

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


    Book(long time, String category, String url) {
        timestamp = time;
        name = category;
        this.url = url;
    }

    public Book() {

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

}
