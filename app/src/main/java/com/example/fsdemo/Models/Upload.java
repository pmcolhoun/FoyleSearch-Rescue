package com.example.fsdemo.Models;

/**
 * Model Class for image upload - contains information for the images uploaded
 */

import com.google.firebase.database.Exclude;

public class Upload {

    private String mName;
    private String mImageUrl;
    private String mKey;

    public Upload() {
        //empty constructor needed
    }

    public Upload(String name, String imageUrl) {
        //In case user forgets to set a date before uploading
        if (name.trim().equals("")) {
            name = "No Date";
        }
        mName = name;
        mImageUrl = imageUrl;
    }//Full constructor

    //Getters and Setters
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    //Exclude from database to avoid redundant data in Firebase
    @Exclude
    public String getKey() {
        return mKey;
    }
    @Exclude
    public void setKey(String key) {
        mKey = key;
    }

}//class