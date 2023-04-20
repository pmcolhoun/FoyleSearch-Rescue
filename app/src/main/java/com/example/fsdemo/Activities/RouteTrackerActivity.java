package com.example.fsdemo.Activities;

/**
 *RouteTrackerActivity displays information about the location tracker and explains how the user can
 * upload their mapped route to the database. The two buttons redirect users accordingly and also
 * contains clickable social media icons.
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.fsdemo.R;

public class RouteTrackerActivity extends AppCompatActivity {

    Button trackBtn;
    Button uploadBtn;

    ImageView facebook;
    ImageView twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_tracker);

        //linking variables to UI design
        trackBtn = (Button) findViewById(R.id.track);
        uploadBtn = (Button) findViewById(R.id.upload);
        facebook = (ImageView) findViewById(R.id.iv_facebook);
        twitter = (ImageView) findViewById(R.id.iv_twitter);

        //Setting action for icon/text-view clicks
        trackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RouteTrackerActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RouteTrackerActivity.this, MapUploadActivity.class);
                startActivity(i);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/foylesearchandrescue/?rf=394465763943378";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://twitter.com/foylerescue?lang=en";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


    }//onCreate()


}//class
