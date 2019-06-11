package com.example.samsproject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminStaffLists extends AppCompatActivity {

    private DatabaseReference dbref;
    private ListView staffListView;
    private List<Staff> staffList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_staff_lists);

        staffListView = findViewById(R.id.staffLists);
        staffList = new ArrayList<>();

        dbref = FirebaseDatabase.getInstance().getReference("Staff Information");

        getSupportActionBar().setTitle("Staffs List");
    }

    @Override
    protected void onStart() {
        super.onStart();

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                staffList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Staff st = snapshot.getValue(Staff.class);
                    staffList.add(st);
                }
                AdminStaffListAdapter adapter = new AdminStaffListAdapter(AdminStaffLists.this, staffList);
                staffListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
