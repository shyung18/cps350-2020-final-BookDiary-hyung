package com.example.bookdiary.ui.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.bookdiary.FetchMyBookLibrary;
import com.example.bookdiary.R;
import com.example.bookdiary.ui.history.HistoryFragment;
import com.example.bookdiary.ui.search.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment {
//implements SearchView.OnQueryTextListener
    private HomeViewModel homeViewModel;
    private BottomNavigationView bottomNavigationView;
    private String authToken;
    private FetchMyBookLibrary fetchMyBookLibrary;
    private String bookTitle = "";
    private String bookAuthors = "";
    private Bitmap bookImage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_current, container, false);
        setHasOptionsMenu(true); // Add this! (as above)
        Bundle bundle = this.getArguments();

        if (bundle != null && bundle.get("authToken") !=null) {
            authToken = bundle.getString("authToken");

        }
        if(bundle.get("title") !=null)
        {
            bookTitle = bundle.getString("title");
            bookAuthors = bundle.getString("authors");
            bookImage = bundle.getParcelable("image");
        }
        else
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
        titleNameView.setText(bookTitle);
        authorNameView.setText(bookAuthors);
        imageView.setImageBitmap(bookImage);

        Button doneButton = root.findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return root;
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
