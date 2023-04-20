package com.example.fsdemo.Activities;

/**
 *MainScreenActivity uses a web-view to displays the news updates from Foyle Search and Rescue's
 * website. The navigation drawer is available from this activity and allows users to easily navigate
 * the application.
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.fsdemo.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainScreenActivity extends AppCompatActivity {

    private DrawerLayout drawerLayoutAdmin;
    private NavigationView navViewAdmin;
    private ActionBarDrawerToggle toggle;
    private Dialog loadingDialog;

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        //Initialise loading dialog for progress bar
        loadingDialog = new Dialog(MainScreenActivity.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        //Navigation drawer support
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerLayoutAdmin = (DrawerLayout)findViewById(R.id.drawer_layout);
        navViewAdmin = (NavigationView)findViewById(R.id.menuLayout);

        toggle = new ActionBarDrawerToggle(this, drawerLayoutAdmin, R.string.open, R.string.close);
        drawerLayoutAdmin.addDrawerListener(toggle);
        toggle.syncState();

        //Navigation drawer menu options
        navViewAdmin.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_home:
                        Toast.makeText(MainScreenActivity.this, "Home Clicked", Toast.LENGTH_SHORT).show();
                        Intent homeIntent = new Intent(MainScreenActivity.this, MainScreenActivity.class);
                        startActivity(homeIntent);
                        break;
                    case R.id.nav_training:
                        Toast.makeText(MainScreenActivity.this, "Training Clicked", Toast.LENGTH_SHORT).show();
                        Intent trainingIntent = new Intent(MainScreenActivity.this, TrainingActivity.class);
                        startActivity(trainingIntent);
                        break;
                    case R.id.nav_routeTracker:
                        Toast.makeText(MainScreenActivity.this, "Route Tracker Clicked", Toast.LENGTH_SHORT).show();
                        Intent routeTrackerIntent = new Intent(MainScreenActivity.this, RouteTrackerActivity.class);
                        startActivity(routeTrackerIntent);
                        break;
                    case R.id.nav_volunteers:
                        Toast.makeText(MainScreenActivity.this, "Volunteers Clicked", Toast.LENGTH_SHORT).show();
                        Intent volunteerIntent = new Intent(MainScreenActivity.this, VolunteersActivity.class);
                        startActivity(volunteerIntent);
                        break;
                    case R.id.nav_donate:
                        Toast.makeText(MainScreenActivity.this, "Donate Clicked", Toast.LENGTH_SHORT).show();
                        Intent donateIntent = new Intent(MainScreenActivity.this, DonateActivity.class);
                        startActivity(donateIntent);
                        break;
                    case R.id.nav_aboutUs:
                        Toast.makeText(MainScreenActivity.this, "About Us Clicked", Toast.LENGTH_SHORT).show();
                        Intent aboutUsIntent = new Intent(MainScreenActivity.this, AboutUsActivity.class);
                        startActivity(aboutUsIntent);
                        break;
                    case R.id.nav_contactUs:
                        Toast.makeText(MainScreenActivity.this, "Contact Us Clicked", Toast.LENGTH_SHORT).show();
                        Intent contactUsIntent = new Intent(MainScreenActivity.this, ContactUsActivity.class);
                        startActivity(contactUsIntent);
                        break;
                    case R.id.nav_logout:
                        Toast.makeText(MainScreenActivity.this, "Logout Clicked", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        Intent logoutIntent = new Intent(MainScreenActivity.this, LoginActivity.class);
                        startActivity(logoutIntent);
                        break;

                }//switch

                return false;
            }//onNavigationItemSelected()
        });


        //Displays Foyle Search and Rescue's news updates to user
        webView = findViewById(R.id.webView);
        //set webview to invisible until page has fully loaded with JavaScript implementations
        webView.setVisibility(View.INVISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            //hides website header for cleaner UI within app
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:(function() { " +
                        "var head = document.getElementsByTagName('header')[0];"
                        + "head.parentNode.removeChild(head);" +
                        "})()");
                loadingDialog.cancel();
                webView.setVisibility(View.VISIBLE);
            }
        });
        webView.loadUrl("https://www.foylesearchandrescue.com/news/");


    }//onCreate


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }//onOptionsItemSelected()

    @Override
    public void onBackPressed() {
        //ensures using back button goes back in web-view and not exit the app
        if(webView.canGoBack()){
            webView.goBack();
        }
        else {
            super.onBackPressed();
        }//else
    }//onBackPressed()


}//class