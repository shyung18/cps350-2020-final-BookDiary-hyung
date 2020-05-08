package com.example.bookdiary.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.bookdiary.FetchMyBookLibrary;
import com.example.bookdiary.MainActivity;
import com.example.bookdiary.R;
import com.example.bookdiary.ui.history.HistoryFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment {
//implements SearchView.OnQueryTextListener
    private HomeViewModel homeViewModel;
    private Context mContext;
    private BottomNavigationView bottomNavigationView;
    private String authToken;
    private FetchMyBookLibrary fetchMyBookLibrary;
    private String bookTitle = "";
    private String bookAuthors = "";
    private Bitmap bookImage;
    private String bookId;
    private Canvas canvas;
    private String reflectionText = "";

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_current, container, false);
        setHasOptionsMenu(true); // Add this! (as above)
        Bundle bundle = this.getArguments();
        mContext = root.getContext();

        authToken = bundle.getString("authToken");

        if(bundle.get("title") !=null)
        {
            bookTitle = bundle.getString("title");
            bookAuthors = bundle.getString("authors");
            bookImage = bundle.getParcelable("image");
        }

        if(bundle.get("reflectionText") !=null)
        {
            reflectionText = bundle.getString("reflectionText");
        }

        if(bundle.get("title") == null)
        {
            fetchMyBookLibrary = new FetchMyBookLibrary("getCurrentRead", authToken);
            String result = "";
            try {
                result = fetchMyBookLibrary.execute().get();
                setData(result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }


        TextView titleNameView = root.findViewById(R.id.main_title);
        TextView authorNameView = root.findViewById(R.id.main_author);
        ImageView imageView = root.findViewById(R.id.main_image);
        Button doneButton = root.findViewById(R.id.doneButton);
        ReflectionView reflectionView = root.findViewById(R.id.rView);
        reflectionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PopUpWindow.class));
            }
        });
        if(reflectionText != "")
        {
            reflectionView.setmText(reflectionText);
            SharedPreferences sharedPref = getActivity().getSharedPreferences("com.example.bookdiary", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(bookId, reflectionText);
            editor.commit();
        }

        if(bookTitle == "")
        {
            titleNameView.setText("No Book in Progress");
            authorNameView.setText("Go search for a new book!");
            imageView.setImageResource(R.drawable.none_icon);
            doneButton.setText("Search");
        }
        else
        {
            titleNameView.setText(bookTitle);
            authorNameView.setText(bookAuthors);
            imageView.setImageBitmap(bookImage);
            doneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FetchMyBookLibrary fetchMyBookLibrary = new FetchMyBookLibrary("removeCurrentRead", authToken);
                    fetchMyBookLibrary.execute(bookId);
                    fetchMyBookLibrary = new FetchMyBookLibrary("postHistoryBook", authToken);
                    fetchMyBookLibrary.execute(bookId);
                    Bundle bundle = new Bundle();
                    bundle.putString("authToken", authToken);
                    switchContent(R.id.fragment_container, new HistoryFragment(), bundle);

                }
            });
        }


        return root;
    }


    public void switchContent(int id, Fragment fragment, Bundle bundle) {
        if (mContext == null)
            return;
        if (mContext instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) mContext;
            Fragment frag = fragment;
            mainActivity.switchContent(id, frag, bundle);
        }

    }

    void setData(String data)
    {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
                JSONObject book = itemsArray.getJSONObject(0);
                String title = null;
                JSONArray authors = null;
                String imageUrl = null;
                bookId = book.getString("id");
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    //imageUrl= volumeInfo.getJSONObject("imageLinks").getString("thumbnail");
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getJSONArray("authors");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (title != null && authors != null) {
                    bookTitle = title;
                    for(int i =0; i<authors.length(); i++)
                    {
                        try{
                            bookAuthors+=authors.get(i).toString() + ", ";
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    bookImage = fetchMyBookLibrary.getCurrentImage();
                }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
