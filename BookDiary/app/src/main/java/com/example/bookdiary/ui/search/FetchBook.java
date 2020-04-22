package com.example.bookdiary.ui.search;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FetchBook extends AsyncTask<String, Void, String> {

    private CardView dataList;
    private ArrayList<String> titles;
    private ArrayList<String> authors;

    FetchBook()
    {
        dataList = new CardView();
        titles = new ArrayList<String>();
        authors = new ArrayList<String>();
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBookInfo(strings[0]);
    }

}
