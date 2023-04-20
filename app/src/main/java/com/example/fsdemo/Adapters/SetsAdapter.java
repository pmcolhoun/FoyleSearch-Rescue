package com.example.fsdemo.Adapters;

/**
 *Class responsible for loading sets into the GridView. SetsAdapter Class is
 * the bridge between the data from Firestore and the GridView - will pass the data from Firestore
 * collections and documents in form of a list of sets for each user.
 */

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fsdemo.Activities.QuestionActivity;
import com.example.fsdemo.R;

public class SetsAdapter extends BaseAdapter {

    private int numOfSets;

    //Assigns catList from CategoryActivity to CategoryGridAdapter
    public SetsAdapter(int numOfSets) {
        this.numOfSets = numOfSets;
    }//constructor

    @Override
    public int getCount() {
        return numOfSets;
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

        if (convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_item_layout, parent, false);
        }else{
            view = convertView;
        }

        //redirects user to question activity
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), QuestionActivity.class);
                intent.putExtra("SETNO", position + 1);
                parent.getContext().startActivity(intent);
            }
        });

        ((TextView) view.findViewById(R.id.setNo_tv)).setText(String.valueOf(position + 1));

        return view;
    }
}