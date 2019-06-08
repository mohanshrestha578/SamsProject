package com.example.samsproject.Activities.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.samsproject.Additem;
import com.example.samsproject.Models.ModelItem;
import com.example.samsproject.Models.Role;
import com.example.samsproject.R;
import com.example.samsproject.RecyclerViews.Admin.RecyclerViewAdminItem;
import com.example.samsproject.RecyclerViews.RecyclerViewRole;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Item extends AppCompatActivity {

    private static final String TAG = "AdminItemActivity";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;

    private List<ModelItem> items;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        items = new ArrayList<>();

        progress = new ProgressDialog(this);

        myRef = database.getReference("Items");

        getAllItems();

    }

    private void getAllItems() {

        startLoader(progress);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                items.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ModelItem item = postSnapshot.getValue(ModelItem.class);
                    items.add(item);
                }

                initRecyclerViewAdminItem();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                endLoader(progress);
            }
        });

    }

    private void initRecyclerViewAdminItem() {
        Log.d(TAG, "initRecyclerViewAdminItem: from ItemActivity");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerView = findViewById(R.id.show_rv_items);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdminItem viewItem = new RecyclerViewAdminItem(this, items);
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


    public void createAdminItem(View view) {
        startActivity(new Intent(getApplicationContext(), Additem.class));
    }
}
