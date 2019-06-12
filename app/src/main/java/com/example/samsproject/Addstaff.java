package com.example.samsproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.samsproject.Activities.RolesActivity;
import com.example.samsproject.Models.Role;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    private FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();

        dbref = FirebaseDatabase.getInstance().getReference("Staff Information");

        addRolesOfStaff();

        //initialize Adapter and set value
        dataadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles_list);
        dataadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role_name.setAdapter(dataadapter);
        dataadapter.notifyDataSetChanged();

        getSupportActionBar().setTitle("Add Staff");
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

                mAuth.createUserWithEmailAndPassword(staffEmailAddress, staffPass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                } else {

                                }

                                // ...
                            }
                        });

                Toast.makeText(this, "Staff has been successfully added", Toast.LENGTH_SHORT).show();
                notification();
                name.setText("");
                phoneNumber.setText("");
                year.setText("");
                staffEmail.setText("");
                staffPassword.setText("");
                staffConfirmPassword.setText("");
                address.setText("");
            }
    }

    public void notification(){
        Context context = Addstaff.this;

        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0,i,0);// pending intent runs the application in background

        NotificationManager nm = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);// notification manager build the notification


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notifChannel = new NotificationChannel("1", "MyChannel", NotificationManager.IMPORTANCE_DEFAULT); //decide to merge the notificaion or not

            notifChannel.enableLights(true);
            notifChannel.setLightColor(Color.RED);
            notifChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notifChannel.enableVibration(true);
            nm.createNotificationChannel(notifChannel);
        }

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context, "1");

        notifBuilder.setAutoCancel(true)//cancel the notification after opening the app
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())// sets time
                .setSmallIcon(android.R.drawable.star_big_on)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle("Staff Added")
                .setContentText("You have successfully addded staff")
                .setContentIntent(pi);//helps to run in background

        nm.notify(0, notifBuilder.build());//shows the notification
    }
}
