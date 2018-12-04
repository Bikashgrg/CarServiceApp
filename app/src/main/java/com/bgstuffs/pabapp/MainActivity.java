package com.bgstuffs.pabapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    // declaration of the views
    private Button login, signup, forgotPassword;
    private RelativeLayout rellay1, rellay2;
    private EditText userName, pass;

    // declaration of progress dialog
    private ProgressDialog progressDialog;

    // for making the views visible as it is not visible in the layout
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };

    // declaration of Fire base
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        // initialization of the views
        rellay1 = findViewById(R.id.rellay1);
        rellay2 = findViewById(R.id.rellay2);
        userName = findViewById(R.id.etUser);
        pass = findViewById(R.id.etPass);
        login = findViewById(R.id.buttonLogin);
        signup = findViewById(R.id.buttonSignup);
        forgotPassword = findViewById(R.id.buttonForgot);

        // initialization of progress dialog
        progressDialog = new ProgressDialog(this);

        // for the timing of the starting of the app
        handler.postDelayed(runnable,2000);

        // when the login button is clicked
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //userLogin();
                loginProcess();
            }
        });

        // when sign up button is clicked
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start signUp activity
                startActivity(new Intent(MainActivity.this,signUp.class));
            }
        });

        // when forgot password is clicked
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start forgetPassword activity
                startActivity(new Intent(MainActivity.this,forgetPassword.class));
            }
        });

    }


    private void loginProcess(){
        try {
            // converting views into strings
            String email = userName.getText().toString().trim();
            String password = pass.getText().toString().trim();

            // when email and password is empty(0) or null
            if (TextUtils.isEmpty(email)){
                userName.setError("email cannot be empty");
            }
            if (TextUtils.isEmpty(password)){
                pass.setError("password cannot be empty");
            }

            // loading while login in


            // to check validation for sign in with valid fire base data
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    progressDialog.setMessage("login in process..");
                    progressDialog.show();
                    if (task.isSuccessful()){
                        // finish() just lets the system know that the programmer wants the
                        // current Activity to be finished. And hence, it calls up onDestroy() after that
                        finish();

                        // start the profile activity
                        startActivity(new Intent(MainActivity.this,afterLogin.class));
                    }

                    else {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this,"Invalid username/password",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }


    }


}
