package com.example.samsproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminTableLists extends AppCompatActivity {

    private DatabaseReference dbref;
    private ListView listView;
    private List<Table> tableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_table_lists);

        listView = findViewById(R.id.tableLists);
        tableList = new ArrayList<>();

        dbref = FirebaseDatabase.getInstance().getReference().child("Table Information");

    }

    @Override
    protected void onStart() {
        super.onStart();

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tableList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Table tbo = snapshot.getValue(Table.class);
                    tableList.add(tbo);
                }

                AdminTableListAdapter adapter = new AdminTableListAdapter(AdminTableLists.this, tableList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
