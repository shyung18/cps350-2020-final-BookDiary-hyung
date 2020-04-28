package com.example.bookdiary.ui.home;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private String title;
    private String authors;
    private Bitmap image;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setData(String t, String a, Bitmap i)
    {
        title = t;
        authors = a;
        image = i;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getAuthors() {
        return authors;
    }

    public String getTitle() {
        return title;
    }
}