package com.example.fsdemo.Activities;

/**
 *SignUpActivity allows users to sign up using their email address and password which is then passed
 * to Firebase were the users can then be authenticated. After user's sign-up they are redirected to
 * the MainScreenActivity or they can use a clickable text-view to redirect them to the LoginActivity.
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
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    public FirebaseAnalytics userId;
    EditText email, password;
    Button signUpBtn;
    TextView signInTV;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Creating an instance for Firebase authentication
        mFirebaseAuth = FirebaseAuth.getInstance();

        //linking variables to UI design
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signUpBtn = findViewById(R.id.signUp);
        signInTV = findViewById(R.id.signIn);

        //Setting action for icon/text-view clicks
        signUpBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
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
                    Toast.makeText(SignUpActivity.this, "Both fields are empty!", Toast.LENGTH_SHORT).show();
                }//else if emailAddress && pwd is empty
                //if user has entered details - create user
                else if(!(emailAddress.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(emailAddress, pwd).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(!task.isSuccessful()){
                                Toast.makeText(SignUpActivity.this, "Sign Up Failed! Please try again", Toast.LENGTH_SHORT).show();
                            }//if
                            else{
                                //if login is successful move to HomeScreen
                                startActivity(new Intent(SignUpActivity.this, MainScreenActivity.class));
                            }//else

                        }//onComplete()
                    });//addOnCompleteListener()
                }//else if email and pwd entered
                else{
                    Toast.makeText(SignUpActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }//else
            }
        });

        signInTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(i);
            }//onClick()
        });//setOnClickListener()

    }//onCreate()

}//class