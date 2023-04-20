package com.example.fsdemo.Activities;

/**
 *ScoreActivity displays the user's score from whichever quiz the user has taken. The "done" button
 * then redirects the user back to the MainScreenActivity.
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fsdemo.R;

public class ScoreActivity extends AppCompatActivity {

    private TextView score;
    private Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        score = findViewById(R.id.sa_score);
        done = findViewById(R.id.sa_done);

        //Pass users score to the intent
        String score_str = getIntent().getStringExtra("SCORE");
        score.setText(score_str);

        //OnClick Methods
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Returns user to main screen and ends Score Activity
                Intent intent = new Intent(ScoreActivity.this, MainScreenActivity.class);
                ScoreActivity.this.startActivity(intent);
                ScoreActivity.this.finish();
            }
        });

    }//onCreate()

}//class