package com.example.fsdemo.Activities;

/**
 *LoginActivity allows users to login into the application; user's credentials are authenticated
 * through Firebase before being granted accesses to the application. User's can also be redirected
 * to the SignUpActivity using a clickable text-view.
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fsdemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button loginBtn;
    TextView signUpTV;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creating an instance for Firebase authentication
        mFirebaseAuth = FirebaseAuth.getInstance();

        //linking variables to UI design
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login);
        signUpTV = findViewById(R.id.signUp);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                //using Firebase AuthStateListener to check if the user email and password exist so user can login
                if(mFirebaseUser != null){
                    Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, MainScreenActivity.class);
                    startActivity(i);
                }//if
                else{
                    Toast.makeText(LoginActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }//else
            }//onAuthStateChanged()
        };//new FirebaseAuth.AuthStateListener()


        //Setting action when you press login button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = email.getText().toString();
                String pwd = password.getText().toString();

                //checking if email field is empty
                if(emailAddress.isEmpty()){
                    email.setError("Please enter email address");
                    email.requestFocus();
                }//if
                //checking if password field is empty
                else if (pwd.isEmpty()){
                    password.setError("Please enter password");
                    password.requestFocus();
                }//else if pwd is empty
                //checking if both email and password are empty
                else if(emailAddress.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Both fields are empty!", Toast.LENGTH_SHORT).show();
                }//else if emailAddress && pwd is empty
                //if user has entered details - create user
                else if(!(emailAddress.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(emailAddress, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Login Error! Please try again", Toast.LENGTH_SHORT).show();
                            }//if
                            else{
                                Intent i = new Intent(LoginActivity.this, MainScreenActivity.class);
                                startActivity(i);
                            }//else

                        }//onComplete()
                    });//addOnCompleteListener()
                }//else if email and pwd entered
                else{
                    Toast.makeText(LoginActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }//else

            }//onClick()
        });//onClickListener()


        signUpTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }//onClick()
        });//setOnClickListener()


    }//onCreate()

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }//onStart()


}//class