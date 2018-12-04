package com.bgstuffs.pabapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class signUp extends AppCompatActivity {

    // declaration of the views
    private Button btn;
    private EditText fullname,email,password,cpassword,carname,mob;
    private ProgressBar progressBar;

    // declaration of Fire Base
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // initialization of the views
        fullname = findViewById(R.id.etFirstName);
        email = findViewById(R.id.etLastName);
        password = findViewById(R.id.etEmail);
        cpassword = findViewById(R.id.etPassword);
        carname = findViewById(R.id.etConfirmPassword);
        mob = findViewById(R.id.etNumber);
        btn = findViewById(R.id.btnSignUp);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser(){
        final String uName = fullname.getText().toString().trim();
        final String uEmail = email.getText().toString().trim();
        final String uPassword = password.getText().toString().trim();
        final String uCar = carname.getText().toString().trim();
        final String uMobile = mob.getText().toString().trim();

        if (uName.isEmpty()) {
            fullname.setError("Name cannot be empty");
        }

        if (uEmail.isEmpty()) {
            email.setError("Enter a valid email address");
        }
        if (TextUtils.isEmpty(uPassword)){
            // password is empty
            password.setError("combination of alphabet and numbers");
        }

        if (TextUtils.isEmpty(uCar)){
            // password is empty
            cpassword.setError("combination of alphabet and numbers");
        }

        if ((uMobile.length()<10) || (uMobile.length()>10)){
            // mobile is empty
            mob.setError("enter a valid 10 digit number");
        }

        else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(uEmail,uPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){

                        Toast.makeText(signUp.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(signUp.this,MainActivity.class));
                    }
                    else {
                        Toast.makeText(signUp.this,"User Authentication Failed",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }



    }

}
