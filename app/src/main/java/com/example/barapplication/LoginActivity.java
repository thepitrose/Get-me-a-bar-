package com.example.barapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private EditText emaillog;
    private EditText passwordlog;
    private Button loginlog;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        emaillog = findViewById(R.id.emaillog);
        passwordlog = findViewById(R.id.passwordlog);
        loginlog = findViewById(R.id.loginlog);

        auth = FirebaseAuth.getInstance();

        loginlog.setOnClickListener(new View.OnClickListener() {            // get the data from the user interface
            @Override
            public void onClick(View v) {
                String txt_email = emaillog.getText().toString();
                String txt_password = passwordlog.getText().toString();
                loginUser(txt_email, txt_password);
            }
        });
    }

    private void loginUser(String email, String password) {

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this , new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(LoginActivity.this , "login successfull" , Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainLogin.class));                    //If a connection is established, go to the categories window
                    finish();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Failed to login, check email Or password", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



}