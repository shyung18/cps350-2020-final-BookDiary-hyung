package com.example.bookdiary.ui.search;

import android.accounts.AccountManager;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import com.example.bookdiary.BuildConfig;
//import com.fasterxml.jackson.core.JsonFactory;
import com.google.android.gms.auth.GoogleAuthUtil;
//import com.google.api.client.http.HttpHeaders;
//import com.google.api.client.http.HttpRequest;
//import com.google.api.client.http.HttpRequestInitializer;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.http.LowLevelHttpRequest;
//import com.google.api.services.books.Books;
//import com.google.api.services.books.BooksRequest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;

public class NetworkUtils {

    private static final String URL = "https://www.googleapis.com/books/v1/volumes?";
    private static final String QUERY_PARAM = "q";
    private static final String MAX_RESULTS = "maxResults";
    private static final String PRINT_TYPE = "printType";
    private String query;
    private static final String apiKey = "AIzaSyDvrRpcbjbynXQcKPLGBzwkKixkhO_XKj4";

    public static String getBookInfo(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = "";
        Uri builtUri = Uri.parse(URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, queryString)
                .appendQueryParameter(MAX_RESULTS, "10")
                .appendQueryParameter(PRINT_TYPE, "books")
                .build();

        try {
            URL requestURL = new URL("https://www.googleapis.com/books/v1/volumes?q=" + queryString + "&maxResults=10&printType=books");
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            //urlConnection.setRequestMethod("GET");

            int code = urlConnection.getResponseCode();
            //urlConnection.getRequestMethod();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
            bookJSONString = buffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //Log.v("bookjson", bookJSONString.toString());
            return bookJSONString;
        }
    }

    String code_verifier() {
        // Dependency: Apache Commons Codec
        // https://commons.apache.org/proper/commons-codec/
        // Import the Base64 class.
        // import org.apache.commons.codec.binary.Base64;
        SecureRandom sr = new SecureRandom();
        byte[] code = new byte[32];
        sr.nextBytes(code);
        String verifier = Base64.encodeToString(code, Base64.URL_SAFE | Base64.NO_WRAP | Base64.NO_PADDING);
        return verifier;
    }


    public static String getReadBooks(String token) {
        String result = "";
        try {

            URL url = new URL("https://www.googleapis.com/books/v1/mylibrary/bookshelves/4/volumes?key=" + apiKey);
            URLConnection conn = (HttpURLConnection) url.openConnection();
            conn.addRequestProperty("client_id", "690614837290-93ha31tsre16oijd8lhld38i1gdad9m9.apps.googleusercontent.com");
            conn.addRequestProperty("client_secret", "f_UPM44IW2JFrE1UErAeHVPJ");
            conn.setRequestProperty("Authorization", "OAuth " + token);
            int code = ((HttpURLConnection) conn).getResponseCode();
            InputStream inputStream = conn.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
            result = buffer.toString();

            if (conn != null) {
                ((HttpURLConnection) conn).disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getCurrentRead(String token) {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String result = "";
        try {

            URL url = new URL("https://www.googleapis.com/books/v1/mylibrary/bookshelves/3/volumes?key=" + apiKey);
            conn = (HttpURLConnection) url.openConnection();
            conn.addRequestProperty("client_id", "690614837290-93ha31tsre16oijd8lhld38i1gdad9m9.apps.googleusercontent.com");
            conn.addRequestProperty("client_secret", "f_UPM44IW2JFrE1UErAeHVPJ");
            conn.setRequestProperty("Authorization", "OAuth " + token);
            int code = ((HttpURLConnection) conn).getResponseCode();
            InputStream inputStream = conn.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
            result = buffer.toString();

            if (conn != null) {
                ((HttpURLConnection) conn).disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.v("result", result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String postCurrentRead(String authToken, String bookId) {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String result = "";
        OutputStream out = null;
        try {
            URL url = new URL("https://www.googleapis.com/books/v1/mylibrary/bookshelves/3/addVolume?volumeId=" + bookId + "&key=" + apiKey);
            conn = (HttpURLConnection) url.openConnection();
            conn.addRequestProperty("client_id", "690614837290-93ha31tsre16oijd8lhld38i1gdad9m9.apps.googleusercontent.com");
            conn.addRequestProperty("client_secret", "f_UPM44IW2JFrE1UErAeHVPJ");
            conn.addRequestProperty("Content-Type", "application/json");
            conn.addRequestProperty("Content-Length", "CONTENT_LENGTH");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "OAuth " + authToken);


            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }

            String text = sb.toString();
            System.out.println(conn.getResponseCode());

//
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
//            writer.write(data);
//            writer.flush();
//            writer.close();
//            out.close();
            if (conn != null) {
                ((HttpURLConnection) conn).disconnect();
            }

            return result;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
