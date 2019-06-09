package com.example.samsproject;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.samsproject.Activities.RolesActivity;
import com.example.samsproject.Models.Role;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Addstaff extends AppCompatActivity {

    private EditText name;
    private EditText phoneNumber;
    private EditText year;
    private EditText staffEmail;
    private EditText staffPassword;
    private EditText staffConfirmPassword;
    private EditText address;
    private Button addStaff;
    private Spinner role_name;

    List<String> roles_list = new ArrayList<>();

    ArrayAdapter<String> dataadapter;

    private DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstaff);

        name = findViewById(R.id.staffname);
        phoneNumber = findViewById(R.id.staffPhoneno);
        year = findViewById(R.id.joinYear);
        staffEmail = findViewById(R.id.staffEmail);
        staffPassword = findViewById(R.id.staffPassword);
        staffConfirmPassword = findViewById(R.id.staffConfirmPassword);
        address = findViewById(R.id.staffLocation);

        role_name = findViewById(R.id.roleSpinner);

        dbref = FirebaseDatabase.getInstance().getReference("Staff Information");

        addRolesOfStaff();

        //initialize Adapter and set value
        dataadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles_list);
        dataadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role_name.setAdapter(dataadapter);
        dataadapter.notifyDataSetChanged();

    }

    private void addRolesOfStaff() {
        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("roles");

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                roles_list.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Role ra = snapshot.getValue(Role.class);
                    roles_list.add(ra.getRole_name());
                }
                dataadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addStaff(View view) {
        String staffName = name.getText().toString();
        String staffPhone = phoneNumber.getText().toString();
        String staffJoinYear = year.getText().toString();
        String staffEmailAddress = staffEmail.getText().toString();
        String staffPass = staffPassword.getText().toString();
        String staffConPass = staffConfirmPassword.getText().toString();
        String staffLocation = address.getText().toString();
        String roleSpinner = String.valueOf(role_name.getSelectedItem());

        if(TextUtils.isEmpty(staffName) && name.getText().length()<=3) {
            Toast.makeText(this, "Please, Enter the name of the staff", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(staffPhone) && phoneNumber.getText().length()==10){
            Toast.makeText(this, "Please, Enter the correct contact number", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(staffJoinYear) && year.getText().length()==4){
            Toast.makeText(this, "Please, Enter the joining date of the staff", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(staffEmailAddress)){
            Toast.makeText(this, "Please, Enter the email address of the staff", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(staffLocation)){
            Toast.makeText(this, "Please, Enter the location where staff lives", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(staffPass) && (staffPassword.getText().length()<=6)){
            Toast.makeText(this, "Please, Enter the password", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(staffConPass) && staffConfirmPassword.getText().length()<=6){
            Toast.makeText(this, "Please, Enter password for confirmation", Toast.LENGTH_SHORT).show();
        }else if(!staffPass.equals(staffConPass)){
            Toast.makeText(this, "Make sure your both password are correct", Toast.LENGTH_SHORT).show();
        }else{
                String staffId = dbref.push().getKey();
                Staff staff = new Staff(staffId, staffName, staffPhone, staffJoinYear, staffEmailAddress, staffPass, staffConPass, staffLocation, roleSpinner);
                dbref.child(staffId).setValue(staff);
                Toast.makeText(this, "Staff has been successfully added", Toast.LENGTH_SHORT).show();
                name.setText("");
                phoneNumber.setText("");
                year.setText("");
                staffEmail.setText("");
                staffPassword.setText("");
                staffConfirmPassword.setText("");
                address.setText("");
            }
    }
}
