package com.example.fsdemo.Activities;

/**
 *SetsActivity displays the amount of sets that are available for the category chosen by the user to
 * take a quiz on. The sets are loaded in from the Firestore using the SetsAdapter to pass the data
 * in correctly to the View.
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.fsdemo.Adapters.SetsAdapter;
import com.example.fsdemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SetsActivity extends AppCompatActivity {

    public static int category_id; //public variable so id is accessible throughout application
    private GridView setsGrid;
    private FirebaseFirestore firestore;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        //Assigning the Category ID which can be retrieved when querying Firebase
        category_id = getIntent().getIntExtra("CATEGORY ID", 1);

        setsGrid = findViewById(R.id.sets_gridView);

        //Initialise loading dialog for progress bar
        loadingDialog = new Dialog(SetsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        //Retrieving data for set from Firebase
        firestore = FirebaseFirestore.getInstance();
        loadSets();

        /*
         Locally assigning number of sets by hard-coding on device - does not retrieve data from Firebase
         Assigns the number of sets per test
         SetsAdapter adapter = new SetsAdapter(3);
         setsGrid.setAdapter(adapter);
        */

    }//onCreate()

    public void loadSets(){
        firestore.collection("QUIZ").document("CAT" + String.valueOf(category_id))
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                //checking if query was successful or failed
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();

                    if (doc.exists()) {
                        //contains number of sets each category has
                        long sets = (long) doc.get("SETS");
                        //Setting the adapter to specific set view
                        SetsAdapter adapter = new SetsAdapter(Integer.valueOf((int) sets));
                        setsGrid.setAdapter(adapter);
                    }//if
                    else {
                        //Query is successful but doesn't find any documents on the server
                        Toast.makeText(SetsActivity.this, "No CAT Document Exists", Toast.LENGTH_SHORT).show();
                    }//else
                } else {
                    //If there is a failure loading data displays toast message to user
                    Toast.makeText(SetsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }//else
                //cancel loading progress bar once data is retrieved
                loadingDialog.cancel();
            }
        });
    }//loadSets()

}//class