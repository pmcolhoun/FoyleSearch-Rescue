package com.example.fsdemo.Activities;

/**
 *MapUploadActivity allows users to access their devices gallery and select and image which is displayed
 * in a card view before user uploads. User can then upload to Firebase database - progress bar is displayed
 * during upload and appropriate toast message displayed upon upload success/failure
 */

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fsdemo.Models.Upload;
import com.example.fsdemo.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class MapUploadActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private Button chooseMapBtn;
    private Button uploadBtn;

    private TextView viewMaps;
    private EditText mapDetails;
    private ImageView imageView;

    private ProgressBar progressBar;
    private Uri mImageUri; //points to the image so it can be displayed in the image-view and upload it to Firebase Storage

    private StorageReference mStorageRef;

    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

    private FirebaseUser firebaseUser;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_upload);

        //Linking variables to UI
        chooseMapBtn = findViewById(R.id.chooseMap);
        uploadBtn = findViewById(R.id.upload);
        viewMaps = findViewById(R.id.viewMaps);
        mapDetails = findViewById(R.id.mapDetails);
        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);

        //Retrieving current users unique ID from Firebase and assigning it to userId variable
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userId = firebaseUser.getUid();

        //Database variables for Firebase storage - saves image in folder called uploads in Firebase database
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads/User:" + userId);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads/User:" + userId);

        //OnClickListener Methods
        chooseMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ensures user can not upload multiple images at once
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(MapUploadActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

        viewMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagesActivity();
            }
        });

    }//onCreate()


    //Method to find the image data
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }//onFileChooser()

    //Retrieve image data
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(imageView);
        }
    }//onActivityResult()

    //Method to return file extension for the image (.jpeg, .PNG etc.)
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }//getFileExtension()

    //Method to upload image to Firebase - displays toast message to user if upload successful
    private void uploadFile() {
        if (mImageUri != null) {
            //Sets the unique name of each image uploaded to the time in milliseconds to ensure no uploads are overwritten
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            //Adding the image to the database - handles upload success / failure / progress
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //Delay display of progressbar for 5 seconds to user so they can see upload percentage
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            }, 500);

                            //Toast message to tell user upload is successful
                            Toast.makeText(MapUploadActivity.this, "Upload successful", Toast.LENGTH_LONG).show();

                            //Creates new database entry with unique ID so files do not get overwritten
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();
                            Upload upload = new Upload(mapDetails.getText().toString().trim(),downloadUrl.toString());

                            //Creates a new entry in database and assigns it with a unique id - then set its data to the upload file
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);

                            /*
                            Upload upload = new Upload(mapDetails.getText().toString().trim(),
                                    taskSnapshot.getUploadSessionUri().toString());
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);
                             */

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Displays toast error message if upload fails
                            Toast.makeText(MapUploadActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //Displays the upload progress bar to the user
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }//uploadFile()

    private void openImagesActivity(){
        Intent intent = new Intent(this, ImagesActivity.class);
        startActivity(intent);
    }//openImagesActivity

}//class