package com.example.fsdemo.Activities;

/**
 *DonateActivity displays information about Foyle Search and Rescue and how they rely on donations and
 * the different ways users can donate. Donate button redirects users to Foyle Search and Rescue's PayPal
 * where users can make a one off donation, clickable text view redirects users to web-page were they
 * can sign up to donate on a "give-as-you-earn-basis". Also contains an image-view and clickable social media icons.
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fsdemo.R;

public class DonateActivity extends AppCompatActivity {

    Button donateBtn;
    TextView clickHere;
    ImageView facebook;
    ImageView twitter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        //Linking variables to UI design
        donateBtn = (Button) findViewById(R.id.donate_btn);
        clickHere = (TextView) findViewById(R.id.tv_click_here);
        facebook = (ImageView) findViewById(R.id.iv_facebook);
        twitter = (ImageView) findViewById(R.id.iv_twitter);

        //Setting action for icon/text-view clicks to redirect user from TextView to Business donation page
        clickHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.cafonline.org/giving-as-a-company";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        //Setting action for icon/text-view clicks to redirect user from Button to PayPal donation page
        donateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.paypal.com/donate/?token=6AxaVqElevqWV25g5JcNFu6LhH0dv2p3lI0qD-fV3q1iNcS12nJA9ExhAX4No9QlybRLJG&country.x=GB&locale.x=GB";
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

}//class