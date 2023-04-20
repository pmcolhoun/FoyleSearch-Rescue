package com.example.fsdemo.Adapters;

/**
 *Class responsible for loading images into the Recycler View. ImageAdapter Class is
 * the bridge between the data and the Recycler View - will pass the data from the ImagesActivity
 * in form of a list of upload items for each user.
 */

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fsdemo.Models.Upload;
import com.example.fsdemo.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Upload> mUploads;
    private OnItemClickListener mListener;

    public ImageAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }//constructor

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Returns ImageView holder with appropriate View passed in
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        //References current item uploaded
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        //Implement Picasso - allows images to load more efficiently
        Picasso.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.drawable.foyle_search_logo)
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        //show the amount of items that user has uploaded
        return mUploads.size();
    }

    //Create View Holder Class
    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        //Variables for TextView and ImageView items
        public TextView textViewName;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            //Linking variables to UI
            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.map_view_upload);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

        //Handles the Context Menu when clicked
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");

            MenuItem delete = menu.add(Menu.NONE, 1,  1, "Delete");

            delete.setOnMenuItemClickListener(this);
        }

        //Handles menu item clicks
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    switch (item.getItemId()){
                        case 1:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }//class

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    //Method to set Activity as the Listener for the interface
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

}//class
