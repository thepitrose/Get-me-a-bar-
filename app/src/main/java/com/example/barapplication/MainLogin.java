package com.example.barapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainLogin extends AppCompatActivity {

    private Button addbar;
    private Button getbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        getSupportActionBar().setTitle("Tomer & Ziv - Want a bar! ");

        addbar = findViewById(R.id.addbar);
        getbar = findViewById(R.id.getbar);

        getbar.setOnClickListener(new View.OnClickListener() {            // get the data from the user interface
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainLogin.this, getAbar.class));                    //If a connection is established, go to the categories window
                //finish();
            }
        });

        addbar.setOnClickListener(new View.OnClickListener() {            // get the data from the user interface
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainLogin.this, addAbar.class));                    //If a connection is established, go to the categories window
                //finish();
            }
        });

    }
}