package com.example.fsdemo.Activities;

/**
 *SplashActivity is the initial launch Activity for the application, displaying the Foyle Search and
 * Rescue logo and an animation for the text.
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.fsdemo.R;

import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {

    private TextView appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Linking variables to UI
        appName = findViewById(R.id.foyle_search_rescue_tv);

        //Set font style for TextView
        Typeface typeface = ResourcesCompat.getFont(this, R.font.blacklist);
        appName.setTypeface(typeface);

        //Set animation for TextView
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.myanim);
        appName.setAnimation(anim);

        //Thread to sleep for 3 seconds then opens main activity
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        }).start();

    }//onCreate()


}//class
