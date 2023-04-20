package com.example.fsdemo.Adapters;

/**
 *Class responsible for loading categories into the GridView. CategoryGridAdapter Class is
 * the bridge between the data from Firestore and the GridView - will pass the data from Firestore
 * collections and documents in form of a list of categories for each user.
 */

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fsdemo.R;
import com.example.fsdemo.Activities.SetsActivity;

import java.util.List;
import java.util.Random;

public class CategoryGridAdapter extends BaseAdapter {

    private List<String> catList;

    //Assigns catList from TrainingActivity to CategoryGridAdapter
    public CategoryGridAdapter(List<String> catList) {
        this.catList = catList;
    }//constructor

    @Override
    public int getCount() {
        return catList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view;
        //Check if the view we are retrieving is convertView and if equal to null then we assign view to parent
        if(convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item_layout, parent, false);
        } else{
            view = convertView;
        }

        //When user clicks category they will be redirected to SetsActivity
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), SetsActivity.class);
                intent.putExtra("CATEGORY", catList.get(position));
                intent.putExtra("CATEGORY ID", position + 1);
                parent.getContext().startActivity(intent);
            }
        });

        //Set TextView to category list
        ((TextView) view.findViewById(R.id.catName)).setText(catList.get(position));
        //set the background colour of categories to random colours
        Random rnd = new Random();
        int colour = Color.argb(255, rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
        view.setBackgroundColor(colour);

        return view;
    }

}//class