package com.example.fsdemo.Activities;

/**
 *ContactUsActivity displays the contact information about Foyle Search and Rescue and how they were founded.
 * Contains a clickable image-view which provides the directions to Foyle Search and Rescue from the user's location
 * and clickable social media icons.
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.fsdemo.R;

public class ContactUsActivity extends AppCompatActivity {

    ImageView facebook;
    ImageView twitter;
    ImageView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        //linking variables to UI design
        facebook = (ImageView) findViewById(R.id.iv_facebook);
        twitter = (ImageView) findViewById(R.id.iv_twitter);
        map = (ImageView) findViewById(R.id.iv_foyleMap);

        //Setting action for icon/text-view clicks
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.google.com/maps/place/Foyle+Search+and+Rescue/@54.992088,-7.325947,14z/data=!4m5!3m4!1s0x0:0x4a7f96d0ff854bc3!8m2!3d54.9842185!4d-7.3325908?hl=en-GB";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
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



}
