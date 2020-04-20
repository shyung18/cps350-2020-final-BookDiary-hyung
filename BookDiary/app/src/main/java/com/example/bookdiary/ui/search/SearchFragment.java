package com.example.bookdiary.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.bookdiary.R;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        //final TextView textView = root.findViewById(R.id.text_dashboard);
        final AutoCompleteTextView searchBar = (AutoCompleteTextView) root.findViewById(R.id.searchBar);
        Button button = (Button) root.findViewById(R.id.searchButton);
        LinearLayout linearLayout = (LinearLayout) root.findViewById(R.id.linearLayout);
        final CardView cardView = new CardView(root.getContext());

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { new FetchBook(cardView).execute(searchBar.getText().toString());
            }
        });
        linearLayout.addView(new CardView(root.getContext()));
//        searchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}
