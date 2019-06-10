package com.example.samsproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateTable extends AppCompatActivity {

    private EditText tableNumber;
    private EditText tableSeats;
    private Button add;
    private DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_table);

        tableNumber = findViewById(R.id.tableNumber);
        tableSeats = findViewById(R.id.numberOfSeats);
        add = findViewById(R.id.addTable);

        dbref = FirebaseDatabase.getInstance().getReference("Table Information");
    }


    public void addtable(View view) {

        String numberOfTable = tableNumber.getText().toString();
        String numberOfSeats = tableSeats.getText().toString();

        if(TextUtils.isEmpty(numberOfTable)){
            Toast.makeText(this, "Please, enter table number", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(numberOfSeats)){
            Toast.makeText(this, "Please, enter number of seats", Toast.LENGTH_SHORT).show();
        }else{
            String id = dbref.push().getKey();
            String bookStatus = "unbooked";
            Table tb = new Table(id, numberOfTable, numberOfSeats, bookStatus);
            dbref.child(id).setValue(tb);
            Toast.makeText(this, "Table successfully created", Toast.LENGTH_SHORT).show();
            tableNumber.setText("");
            tableSeats.setText("");
        }
    }

}
