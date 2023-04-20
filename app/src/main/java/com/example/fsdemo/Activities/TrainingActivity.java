package com.example.fsdemo.Activities;

/**
 *TrainingActivity displays information about the quiz to the user. Two options are displayed to the
 * user, one to take a quiz or another to redirect the user to SCTNI's website. The data for the quiz
 * is loaded through a loadData() method from the Firestore to retrieve the category list.
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fsdemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TrainingActivity extends AppCompatActivity {


    public static List<String> catList = new ArrayList<>();
    private FirebaseFirestore firestore;

    Button startQuizBtn;
    Button certificateBtn;

    ImageView facebook;
    ImageView twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        //Linking variables to UI
        startQuizBtn = findViewById(R.id.quiz);
        certificateBtn = findViewById(R.id.certificate);
        facebook = (ImageView) findViewById(R.id.iv_facebook);
        twitter = (ImageView) findViewById(R.id.iv_twitter);

        //Retrieves data from Firebase server
        firestore = FirebaseFirestore.getInstance();
        new Thread(){
            public void run(){
                loadData();
            }
        }.start();

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

        startQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainingActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });

        certificateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.sctni.co.uk/w/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }//onCreate()

    private void loadData(){
        //Ensures list is clear before loading data
        catList.clear();

        //retrieving categories list from Firestore
        firestore.collection("QUIZ").document("Categories")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                //checking if query was successful or failed
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();

                    if(doc.exists()){
                        long count = (long) doc.get("Count");

                        //loop to count how many documents exist in the database
                        for(int i = 1; i <= count; i++){
                            //Pass the key from the database to String value
                            String catName = doc.getString("CAT" + String.valueOf(i));

                            //store data into the list
                            catList.add(catName);
                        }//for
                    }//if

                    else{
                        //Query is successful but doesn't find any documents on the server
                        Toast.makeText(TrainingActivity.this, "No Category Document Exists", Toast.LENGTH_SHORT).show();
                    }//else
                } else {
                    //If there is a failure loading data displays toast message to user
                    Toast.makeText(TrainingActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }//else
            }//onComplete
        });

    }//loadData()

}//class