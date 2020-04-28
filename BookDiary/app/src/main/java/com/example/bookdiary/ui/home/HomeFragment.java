package com.example.bookdiary.ui.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.bookdiary.R;
import com.example.bookdiary.ui.notifications.NotificationsFragment;
import com.example.bookdiary.ui.search.SearchFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {
//implements SearchView.OnQueryTextListener
    private HomeViewModel homeViewModel;
    private BottomNavigationView bottomNavigationView;

    public HomeFragment() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_current, container, false);
        setHasOptionsMenu(true); // Add this! (as above)


        Bundle bundle = this.getArguments();
        String title = "";
        String authors = "";
        Bitmap bitmap = null;
        if (bundle != null) {
            title = bundle.get("title").toString();
            authors = bundle.get("authors").toString();
            bitmap = bundle.getParcelable("image");
        }

//        ImageView imageView = root.findViewById(R.id.main_image);
//        imageView.setImageBitmap(homeViewModel.getImage());
        TextView titleNameView = root.findViewById(R.id.main_title);
        TextView authorNameView = root.findViewById(R.id.main_author);
        ImageView imageView = root.findViewById(R.id.main_image);
        titleNameView.setText(title);
        authorNameView.setText(authors);
        imageView.setImageBitmap(bitmap);

        RelativeLayout pv = root.findViewById(R.id.description);
        final PaintView paintView = new PaintView(root.getContext());
        pv.addView(paintView);

//        Button eraserToggle = root.findViewById(R.id.eraser);
//        eraserToggle.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                paintView.setErase(true);
//            }
//        });




//        TextView authorNameView = root.findViewById(R.id.main_author);
//        authorNameView.setText(homeViewModel.getAuthors());
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });


        return root;
    }



//    @Override
//    public boolean onQueryTextChange(String query) {
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        // Create fragment and give it an argument specifying the article it should show
//        SearchFragment searchFragment = new SearchFragment();
//        Bundle args = new Bundle();
////        args.putInt(SearchFragment.ARG_POSITION, position);
////        newFragment.setArguments(args);
//
//        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//
//// Replace whatever is in the fragment_container view with this fragment,
//// and add the transaction to the back stack so the user can navigate back
//        transaction.replace(R.id.nav_host_fragment, searchFragment);
//        transaction.addToBackStack(null);
//        //bottomNavigationView.getMenu().getItem(R.id.navigation_search).setChecked(true);
//
//// Commit the transaction
//        transaction.commit();
//        return false;
//    }

}
