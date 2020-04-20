package com.example.bookdiary.ui.search;

import android.os.AsyncTask;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import org.json.JSONArray;
import org.json.JSONObject;

public class FetchBook extends AsyncTask<String, Void, String> {

    private CardView cardView;

    FetchBook(CardView c)
    {
        cardView = c;
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject book = itemsArray.getJSONObject(i);
                String title = null;
                String authors = null;
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (title != null && authors != null) {
                    //TextView textView = new TextView("Her")
                    Log.v("title", title);
                    Log.v("Authorrrr", authors);
                    return;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
