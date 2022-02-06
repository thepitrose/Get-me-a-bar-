package com.example.barapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class getAbar extends AppCompatActivity {

    private ListView listView;       //Statement for user interfaces
    private Button getbutt;



    private ArrayList<String> slist = new ArrayList<>();        // hold the answers to the question which appears
    private ArrayList<String> qlist = new ArrayList<>();        //hold the question the appears
    private ArrayList<String> templist = new ArrayList<>();     //For proper information from the database - Will be expanded later
    private ArrayList<String> alist = new ArrayList<>();        //Receives the entire set of questions and answers from the server
    private String gooda;           // Holds the correct answer to the question which appears

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_abar);

        getSupportActionBar().setTitle("Get me a bar!");

        listView = findViewById(R.id.listView);       //Link user interface to XML
        getbutt=findViewById(R.id.getbutt);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.show, slist);          //what array list will show in  the  listView
        listView.setAdapter(adapter);

        getbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("barlist");   //From which category to bring the questions
                reference.addListenerForSingleValueEvent(new ValueEventListener() {                                             // "Listener" that communicate with database
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {                     //When data is received from the database
                        alist.clear();
                        qlist.clear();
                        slist.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {                  //get the data
                            alist.add(snapshot.getValue().toString());
                            Collections.sort(alist);                                               //Make sure the data theat received will be correct
                        }

                        Collections.shuffle(alist);                                               //to mix the arrangement of questions
                        setAsArrayString(alist);

                        adapter.notifyDataSetChanged();     //Refreshes the view of the question


                    }
                    @Override
                    public void onCancelled (@NonNull DatabaseError error){

                    }
                });

            }
        });


    }

    private void setAsArrayString(ArrayList<String> list)
    {

        String temp;
        String temp2 = list.get(0);         //get The question and the answers
        int a = 0;
        int b = 0;

        for (int i = 1; i < temp2.length() - 1; i++) {
            if (temp2.charAt(i) == 'Q' || temp2.charAt(i) == 'X') //A question in the  Database begins with the letter Q - and answer begins with and X with a follow letter A,B,C,D
            {
                a = i ;
            }

            if (temp2.charAt(i) == 44) {        // all and with a ',' 44 is the ascii of ','
                b = i;
            }

            if (a != 0 && b != 0) {
                templist.add(temp2.substring(a, b));        // add it to tke list
                a = 0;
                b = 0;
            }

        }

        templist.add(temp2.substring(a, temp2.length()-1)); //add the last one , dont end with a ','

        Collections.sort(templist);                 // sort the array

        //-----------------to cut
        slist.clear();
        qlist.clear();

        temp =String.valueOf(templist);

        int x = 0;
        int y = 0;
        for (int i = 0; i < temp.length(); i++) {       //cut the The initial character ,They all start with '='
            if (temp.charAt(i) == 61) {                 //61 is the ascii of '='
                x = i + 1;
            }

            if (temp.charAt(i) == 44) {
                y = i;
            }

            if (x != 0 && y != 0) {
                slist.add(temp.substring(x, y));
                x = 0;
                y = 0;
            }

        }
        slist.add(temp.substring(x, temp.length()-1));
        templist.clear();
        //----------------end cut
    }


}