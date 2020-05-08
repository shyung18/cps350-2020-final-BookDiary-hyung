package com.example.bookdiary;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.bookdiary.ui.home.HomeFragment;
import com.example.bookdiary.ui.history.HistoryFragment;
import com.example.bookdiary.ui.search.SearchFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.android.material.navigation.NavigationView;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.http.GenericUrl;
//import com.google.api.client.http.HttpHeaders;
//import com.google.api.client.http.HttpRequest;
//import com.google.api.client.http.HttpRequestFactory;
//import com.google.api.client.http.HttpRequestInitializer;
//import com.google.api.client.http.HttpResponse;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.http.LowLevelHttpRequest;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.Json;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.JsonGenerator;
//import com.google.api.client.json.JsonParser;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.services.books.Books;
//import com.google.api.services.books.BooksRequest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private SearchFragment searchFragment;
    private BottomNavigationView bottomNavigationView;
    private AppBarConfiguration appBarConfiguration;
    private FragmentTransaction ft;

    private GoogleSignInClient mGoogleSignInClient;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String personId;
    private Uri personPhoto;
    private String authCode;
    private FetchMyBookLibrary fetchMyBookLibrary;
    private Bundle receivedBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.nav_host_fragment_container) != null)
        {
            return;
        }

        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navList);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_notifications)
                .build();

        String serverClientId = getString(R.string.server_client_id);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope("https://www.googleapis.com/auth/books"))
                .requestServerAuthCode(serverClientId)
                .requestIdToken(serverClientId)
                .requestEmail()
                .build();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        account.getIdToken();
        if(account !=null )
        {
            userName = account.getDisplayName();
            firstName = account.getGivenName();
            lastName = account.getFamilyName();
            email = account.getEmail();
            personId = account.getId();
            personPhoto = account.getPhotoUrl();
            authCode = account.getServerAuthCode();
        }
        authCode = "";
        receivedBundle = getIntent().getExtras();
        AccountManager am = AccountManager.get(this);
        Bundle options = new Bundle();
        am.getAuthToken(
                account.getAccount(),                     // Account retrieved using getAccountsByType()
                "Manage your tasks",            // Auth scope
                options,                        // Authenticator-specific options
                this,                           // Your activity
                new OnTokenAcquired(),          // Callback called when a token is successfully acquired
                new Handler(new OnError()));    // Callback called if an error occurs
//        Intent intent = getIntent();
//        //get the received text
//        String receivedText = intent.getStringExtra(Intent.EXTRA_TEXT);
//        if(receivedText != null)
//        {
//
//        }
    }



    private class OnTokenAcquired implements AccountManagerCallback<Bundle> {
        @Override
        public void run(AccountManagerFuture<Bundle> result) {
            // Get the result of the operation from the AccountManagerFuture.
            Bundle bundle = null;
            try {
                bundle = result.getResult();
            } catch (AuthenticatorException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (OperationCanceledException e) {
                e.printStackTrace();
            }

            // The token is a named value in the bundle. The name of the value
            // is stored in the constant AccountManager.KEY_AUTHTOKEN.
            String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
            authCode = bundle.getString(AccountManager.KEY_AUTHTOKEN);
            Bundle b = new Bundle();
            b.putString("authToken", authCode);
            if(receivedBundle.getString("reflectionText")!=null)
            {
                b.putString("reflectionText", receivedBundle.getString("reflectionText"));
            }
            switchContent(R.id.fragment_container, new HomeFragment(), b);
        }
    }

    public void switchContent(int id, Fragment fragment, Bundle data) {

        if(data != null)
        {
            fragment.setArguments(data);
        }
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id, fragment, fragment.toString());
        ft.addToBackStack(null);
        ft.commit();
    }



    private BottomNavigationView.OnNavigationItemSelectedListener navList =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
                {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_search:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.navigation_notifications:
                            selectedFragment = new HistoryFragment();
                            break;
                        case R.id.navigation_home:
                            selectedFragment = new HomeFragment();
                            break;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("authToken", authCode);

                    switchContent(R.id.fragment_container, selectedFragment, bundle);
                    return true;
                }

            };
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }
                });
    }


    private class OnError implements Handler.Callback {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            return false;
        }
    }
}
