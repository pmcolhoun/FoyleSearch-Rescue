package com.example.fsdemo.Activities;

/**
 *CategoryActivity displays the categories available for users to take the quiz questions on. The
 * categories are pulled from the FireStore and displayed in a GridView using the CategoryGridAdapter
 * class to display the View correctly to the user.
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.example.fsdemo.Adapters.CategoryGridAdapter;
import com.example.fsdemo.R;

import static com.example.fsdemo.Activities.TrainingActivity.catList;

public class CategoryActivity extends AppCompatActivity {

    private GridView catGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //linking variables to UI design
        catGrid = findViewById(R.id.catGridView);

        //Retrieving "catList" from TrainingActivity to populate the adapter
        CategoryGridAdapter adapter = new CategoryGridAdapter(catList);
        catGrid.setAdapter(adapter);

/*        Local List hard-coded on device - does not retrieve data from Firebase
//        List<String> catList = new ArrayList<>();
//        //Adding categories to grid view
//        catList.add("Health \n and Safety Training");
//        catList.add("Water Safety \n Training");
 */

    }//onCreate()

}//class