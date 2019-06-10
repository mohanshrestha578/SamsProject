package com.example.samsproject.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.samsproject.Models.Role;
import com.example.samsproject.R;
import com.example.samsproject.RecyclerViews.RecyclerViewRole;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RolesActivity extends AppCompatActivity {

    private static final String TAG = "RoleActivity";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;

    private List<Role> roles;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roles);

        roles = new ArrayList<>();

        progress = new ProgressDialog(this);

        myRef = database.getReference("roles");

        getAllRoles();
    }

    private void getAllRoles() {
        startLoader(progress);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                roles.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Role role = postSnapshot.getValue(Role.class);
                    roles.add(role);
                }

                initRecyclerViewRole();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                endLoader(progress);
            }
        });
    }

    public void createRolePage(View view) {
        startActivity(new Intent(getApplicationContext(), AddRolesActivity.class));
    }

    private void initRecyclerViewRole(){
        Log.d(TAG, "initRecyclerViewRole: from RoleActivity");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerView = findViewById(R.id.show_rv_roles);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewRole viewItem = new RecyclerViewRole(this, roles);
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
