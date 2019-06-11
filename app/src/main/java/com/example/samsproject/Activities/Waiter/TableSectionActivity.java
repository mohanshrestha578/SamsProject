package com.example.samsproject.Activities.Waiter;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.samsproject.R;
import com.example.samsproject.RecyclerViews.Admin.RecyclerViewAdminItem;
import com.example.samsproject.RecyclerViews.Waiter.WaiterTableRecyclerView;
import com.example.samsproject.Table;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TableSectionActivity extends AppCompatActivity {

    private static final String TAG = "TableSectionActivity";
    private List<Table> tables;
    private DatabaseReference dbRef;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_section);

        progress = new ProgressDialog(this);

        tables = new ArrayList<>();

        dbRef = FirebaseDatabase.getInstance().getReference("Table Information");

        startLoader(progress);

        getTableList();
    }

    private void getTableList() {

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Table table = postSnapshot.getValue(Table.class);
                    if(!table.equals(null)){
                        tables.add(table);
                    }
                }

                endLoader(progress);
                initRecyclerViewTableList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                endLoader(progress);
            }
        });

    }

    private void initRecyclerViewTableList() {

        Log.d(TAG, "initRecyclerViewTableList: from TableSectionActivity");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerView = findViewById(R.id.waiter_table_section_rv);
        recyclerView.setLayoutManager(layoutManager);
        WaiterTableRecyclerView viewItem = new WaiterTableRecyclerView(this, tables);
        recyclerView.setAdapter(viewItem);
        endLoader(progress);

    }

    public void startLoader(ProgressDialog progress){
        progress.setMessage("Loading Data...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }

    public void endLoader(ProgressDialog progress){
        progress.dismiss();
    }

}
