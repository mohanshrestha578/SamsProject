package com.example.samsproject.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.samsproject.Models.Category;
import com.example.samsproject.Models.Role;
import com.example.samsproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddRolesActivity extends AppCompatActivity {

    List roles;
    EditText roleName;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private String TAG = "AddRoleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_roles);

        myRef = database.getReference("roles");

        roleName = findViewById(R.id.add_role_name);

        roles = new ArrayList();
    }

    public void createRole(View view) {
        String role_name = roleName.getText().toString().trim();

        if(!TextUtils.isEmpty(role_name)){
            String id = generateId();
            Role role = new Role(id, role_name);

            createRole(role, id);

            roleName.setText("");
            Toast.makeText(this, "New Role Created", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Please enter a role name.", Toast.LENGTH_LONG).show();
        }

    }

    public String generateId(){
        return myRef.push().getKey();
    }

    public void createRole(Role role, String id){
        myRef.child(id).setValue(role);
    }

    public void cancelRole(View view){
        startActivity(new Intent(getApplicationContext(), RolesActivity.class));
    }

}
