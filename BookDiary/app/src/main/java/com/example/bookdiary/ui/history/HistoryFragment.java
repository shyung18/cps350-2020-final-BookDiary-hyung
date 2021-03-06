package com.example.bookdiary.ui.history;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.akshaykale.swipetimeline.TimelineFragment;
import com.akshaykale.swipetimeline.TimelineGroupType;
import com.akshaykale.swipetimeline.TimelineObject;
import com.akshaykale.swipetimeline.TimelineObjectClickListener;
import com.example.bookdiary.FetchMyBookLibrary;
import com.example.bookdiary.R;
import com.example.bookdiary.ui.home.PopUpWindow;
import com.example.bookdiary.ui.search.Items_Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class HistoryFragment extends Fragment {

    private HistoryViewModel notificationsViewModel;
    private String authToken;
    ArrayList<TimelineObject> objs;
    FetchMyBookLibrary fetchMyBookLibrary;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String authors = "";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        objs = new ArrayList<>();
        Bundle bundle = this.getArguments();
        mAdapter = new TimeLine_Adapter(root.getContext(), objs);

        if (bundle != null) {
            authToken = bundle.getString("authToken");
        }
        fetchMyBookLibrary = new FetchMyBookLibrary("getPastReadBooks", authToken);

        // instantiate the TimelineFragment
        TimelineFragment mFragment = new TimelineFragment();

        String result = "";
        try {
            result = fetchMyBookLibrary.execute(authToken).get();
            setData(result);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



//Set data
        mFragment.setData(objs, TimelineGroupType.MONTH);
        mFragment.addOnClickListener(new TimelineObjectClickListener() {
            @Override
            public void onTimelineObjectClicked(TimelineObject timelineObject) {
                Book b = (Book) timelineObject;

                Intent i = new Intent(getContext(), BookUpdate.class);
                i.putExtra("title", timelineObject.getTitle());
                i.putExtra("authors", b.getAuthors());
                i.putExtra("imageUrl", timelineObject.getImageUrl());
                i.putExtra("bookId", b.getBookId());
                startActivity(i);
             }
             @Override
             public void onTimelineObjectLongClicked(TimelineObject timelineObject) {

             }
             });
//Set configurations
                //mFragment.addOnClickListener(this);

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, mFragment);
        transaction.commit();

        return root;
    }

    private void loadData() {
        //Load the data in a list and sort it by times in milli
        String result = "";
        try {
            result = fetchMyBookLibrary.execute(authToken).get();
            setData(result);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void setData(String result) {

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < itemsArray.length(); i++)
            {
                JSONObject book = itemsArray.getJSONObject(i);
                String title = null;
                JSONArray authors = null;
                String imageUrl = null;
                String date = null;
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                String bookId = book.getString("id");

                try {
                    //imageUrl= volumeInfo.getJSONObject("imageLinks").getString("thumbnail");
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getJSONArray("authors");
                    date = book.getJSONObject("userInfo").getString("updated");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (title != null && authors != null && date != null) {

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
                    long dateInLong = 0;
                    try {
                        // The SimpleDateFormat parse the string and return a date object.
                        // To get the date in long value just call the getTime method of
                        // the Date object.
                        Date d = formatter.parse(date);
                        dateInLong = d.getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    Book b = new Book(dateInLong, title, fetchMyBookLibrary.getImageUrl(i));
                    b.setBookId(bookId);
                    b.setAuthors(authors);
                    objs.add(b);
                }
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
            String d1 = "2008-05-01T00:12:45.125Z";
            Date dtemp = sdf.parse(d1);

            objs.add(new Book(dtemp.getTime(), "Book", null));
            d1 = "2012-05-01T00:12:45.125Z";
            dtemp = sdf.parse(d1);
            objs.add(new Book(dtemp.getTime(), "Book", null));
            objs.add(new Book(dtemp.getTime(), "Book", null));
            objs.add(new Book(dtemp.getTime(), "Book", null));
            objs.add(new Book(dtemp.getTime(), "Book", null));
            objs.add(new Book(dtemp.getTime(), "Book", null));

            this.sort(objs);



            //temp data



        }
        catch (Exception e) {
            e.printStackTrace();
        }

        mAdapter.notifyDataSetChanged();
    }

    private void sort(ArrayList<TimelineObject> objs) {
        for (int i = 0; i < objs.size(); i++) {
            long min = objs.get(i).getTimestamp();
            int minId = i;
            for (int j = i+1; j < objs.size(); j++) {
                if (objs.get(j).getTimestamp() > min) {
                    min = objs.get(j).getTimestamp();
                    minId = j;
                }
            }
            TimelineObject temp = objs.get(i);
            objs.set(i, objs.get(minId));
            objs.set(minId, temp);
        }
    }
}
