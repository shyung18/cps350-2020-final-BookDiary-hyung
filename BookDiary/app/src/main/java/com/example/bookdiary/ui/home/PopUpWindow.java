package com.example.bookdiary.ui.home;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bookdiary.MainActivity;
import com.example.bookdiary.R;

public class PopUpWindow extends Activity {
    private Context mContext;
    private FragmentTransaction ft;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        mContext = getApplicationContext();

        getWindow().setLayout((int)(width*.8),(int)(height*.6));
        Button button = findViewById(R.id.submitReflection);
        final EditText textView =  (EditText) findViewById(R.id.reflectionText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("reflectionText", textView.getText().toString());
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("reflectionText", textView.getText().toString());
                startActivity(i);

//                Bundle bundle = new Bundle();
//                bundle.putString("reflectionText", textView.getText().toString());
//                switchContent(R.id.rView, new HomeFragment(), bundle);
            }
        });
    }

    public void switchContent(int id, androidx.fragment.app.Fragment fragment, Bundle bundle) {
        if (mContext == null)
            return;
        if (mContext instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) mContext;
            Fragment frag = fragment;
            mainActivity.switchContent(id, frag, bundle);
        }

    }

}
