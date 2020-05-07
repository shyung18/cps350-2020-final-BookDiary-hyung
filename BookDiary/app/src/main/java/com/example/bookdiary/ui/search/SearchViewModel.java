package com.example.bookdiary.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

// Using a view model provides data persistence, for example when the
// device is rotated (which destroys and recreates the activities).
// https://developer.android.com/topic/libraries/architecture/saving-states
public class SearchViewModel extends ViewModel {
    String getText() {
        return passage;
    }

    void setText(String text) {
        passage = text;
    }

    // Scripture passage, in form ready for display by fragment.
    // It's important for passage to never be null or zero length.
    private String passage = " ";

    /* Bug: Providing an initial value causes a bug where any other
       value is displayed on top of it.
    private String passage =
            "For this is the way God loved the world: " +
            "He gave his one and only Son, so that " +
            "everyone who believes in him will not " +
            "perish but have eternal life. " +
            "John 3:16 (NET)";
     */
}