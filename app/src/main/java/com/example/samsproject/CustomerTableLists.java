package com.example.samsproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerTableLists extends AppCompatActivity {

    private DatabaseReference myref;
    private ListView listView;
    private List<Table> tableLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_table_lists);

        listView = findViewById(R.id.tableLists);
        tableLists = new ArrayList<>();

        myref = FirebaseDatabase.getInstance().getReference().child("Table Information");
    }

    @Override
    protected void onStart() {
        super.onStart();

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tableLists.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Table tbo = snapshot.getValue(Table.class);
                    tableLists.add(tbo);
                }

                CustomerTableListAdapter adapter = new CustomerTableListAdapter(CustomerTableLists.this, tableLists);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}
