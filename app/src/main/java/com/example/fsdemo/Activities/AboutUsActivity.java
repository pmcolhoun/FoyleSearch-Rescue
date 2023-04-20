package com.example.fsdemo.Activities;

/**
 *AboutUsActivity displays information about Foyle Search and Rescue and how they were founded.
 * Contains an image-view and clickable social media icons.
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.fsdemo.R;

public class AboutUsActivity extends AppCompatActivity {

    ImageView facebook;
    ImageView twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        //linking variables to UI design
        facebook = (ImageView) findViewById(R.id.iv_facebook);
        twitter = (ImageView) findViewById(R.id.iv_twitter);

        //Setting action for icon/text-view clicks
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