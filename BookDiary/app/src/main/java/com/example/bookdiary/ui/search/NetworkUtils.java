package com.example.bookdiary.ui.search;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    private static final String URL = "https://www.googleapis.com/books/v1/volumes?";
    private static final String QUERY_PARAM = "q";
    private static final String MAX_RESULTS = "maxResults";
    private static final String PRINT_TYPE = "printType";

    static String getBookInfo(String queryString)
    {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = "";
        Uri builtUri = Uri.parse(URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, "love")
                .appendQueryParameter(MAX_RESULTS, "10")
                .appendQueryParameter(PRINT_TYPE, "books")
                .build();
        Log.i("Please", builtUri.toString());

        try {
            URL requestURL = new URL("https://www.googleapis.com/books/v1/volumes?q=love&maxResults=10&printType=books");
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            //urlConnection.setRequestMethod("GET");

            int code = urlConnection.getResponseCode();
            //urlConnection.getRequestMethod();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if(inputStream == null) {
                //Log.i("input", "null");
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line= reader.readLine()) != null)
            {
                buffer.append(line + "\n");
                //Log.v("line", line);
            }
            if(buffer.length() == 0) {
                return null;
            }
            bookJSONString = buffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

            if(urlConnection!=null)
            {
                urlConnection.disconnect();
            }
            if(reader !=null)
            {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //Log.v("bookJsonString", bookJSONString);
            return bookJSONString;
        }
    }
}
