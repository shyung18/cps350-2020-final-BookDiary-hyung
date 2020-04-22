package com.example.bookdiary.ui.search;

import android.widget.TextView;

import java.util.ArrayList;

public class CardView {
    private ArrayList<String> titles;
    private ArrayList<String> authors;

    CardView() {
        titles = new ArrayList<String>();
        authors = new ArrayList<String>();
    }

    void addTitle(String title)
    {
        titles.add(title);
    }

    void addAuthor(String author)
    {
        authors.add(author);
    }

    String getTitle(int i)
    {
        return titles.get(i);
    }

    String getAuthor(int i)
    {
        return authors.get(i);
    }

}
