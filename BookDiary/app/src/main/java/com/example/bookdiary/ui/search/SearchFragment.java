package com.example.bookdiary.ui.search;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookdiary.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private View root;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Items> dataList;
    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;
    private String query;
    private FetchBook fetchBook;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        root = inflater.inflate(R.layout.fragment_search, container, false);
        setHasOptionsMenu(true);

        searchView = root.findViewById(R.id.searchView);

        dataList = new ArrayList<>();
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        mAdapter = new Items_Adapter(root.getContext(), dataList);
        recyclerView.setAdapter(mAdapter);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);
        getResult();
        return root;
    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }
//

    public void getResult()
    {
        Log.v("listner", "listening");
        // specify an adapter (see also next example)
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                com.example.bookdiary.ui.search.CardView cardView = new com.example.bookdiary.ui.search.CardView();
                fetchBook = new FetchBook();
                com.example.bookdiary.ui.search.CardView cv;
                String result = "";
                try {
                    result = fetchBook.execute(query).get();
                    setData(result);

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    void setData(String data)
    {
        // Create camera layout params
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < itemsArray.length(); i++)
            {
                JSONObject book = itemsArray.getJSONObject(i);
                String title = null;
                JSONArray authors = null;
                String imageUrl = null;
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    //imageUrl= volumeInfo.getJSONObject("imageLinks").getString("thumbnail");
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getJSONArray("authors");
                    Log.v("got URL", "url");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (title != null && authors != null) {

                    Items item = new Items();
                    //Log.v("Authors", authors.get(0).toString());
                    item.setAuthors(authors);
                    item.setTitle(title);
                    //item.setImage(imageUrl);
                    item.setImage(fetchBook.getImages(i));
                    dataList.add(item);
                }
            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }
        mAdapter.notifyDataSetChanged();

    }
}
