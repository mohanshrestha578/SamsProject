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
import android.support.v4.app.NotificationCompat;
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

        getSupportActionBar().setTitle("Create Table");
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
            notification();
            Toast.makeText(this, "Table successfully created", Toast.LENGTH_SHORT).show();
            tableNumber.setText("");
            tableSeats.setText("");
        }
    }
    public void notification(){
        Context context = CreateTable.this;

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
                .setContentTitle("Table Added")
                .setContentText("You have successfully addded table")
                .setContentIntent(pi);//helps to run in background

        nm.notify(0, notifBuilder.build());//shows the notification
    }

}
