package com.example.bookdiary.ui.search;

import android.os.AsyncTask;

import com.example.bookdiary.ui.search.NetworkUtils;

import java.io.IOException;

public class FetchMyBookLibrary extends AsyncTask<String, Void, String> {

    private String authToken;

    public FetchMyBookLibrary(String token) {
        authToken = token;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            String result = NetworkUtils.getReadBooks(authToken);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

}
