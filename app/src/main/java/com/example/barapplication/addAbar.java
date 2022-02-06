package com.example.barapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class addAbar extends AppCompatActivity {

    private EditText nametxt;
    private EditText addresstxt;
    private Button addbutton;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_abar);

        getSupportActionBar().setTitle("Add a new bar to the list");

        nametxt = findViewById(R.id.addName);
        addresstxt = findViewById(R.id.addAddress);
        addbutton = findViewById(R.id.addbutt);

        auth = FirebaseAuth.getInstance();


        addbutton.setOnClickListener(new View.OnClickListener() {            // get the data from the user interface
            @Override
            public void onClick(View v) {
                String name_txt = nametxt.getText().toString();
                String address_txt = addresstxt.getText().toString();


                if (name_txt.isEmpty() || address_txt.isEmpty() )
                {
                    Toast.makeText(addAbar.this , "One or more of the fields are empty" , Toast.LENGTH_SHORT).show();
                }

                else
                {
                    addAquestion(name_txt, address_txt);
                }
            }
        });

    }

    private void addAquestion(String name_txt, String address_txt) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("barlist");
        reference.addListenerForSingleValueEvent(new ValueEventListener(){
            ArrayList<String> temp = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {                  //get the data
                    temp.add(snapshot.getValue().toString());
                }

                String qNum =String.valueOf(temp.size()+1);

                HashMap<String , Object> map = new HashMap<>();

                map.put("Q" , name_txt);
                map.put("XA" , address_txt);


                FirebaseDatabase.getInstance().getReference().child("barlist").child(qNum).updateChildren(map);
                Toast.makeText(addAbar.this , "Add bar successfull" , Toast.LENGTH_SHORT).show();
                startActivity(new Intent(addAbar.this , MainLogin.class));
                finish();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
