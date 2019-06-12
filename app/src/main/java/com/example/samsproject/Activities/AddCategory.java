package com.example.samsproject.Activities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.samsproject.Addstaff;
import com.example.samsproject.MainActivity;
import com.example.samsproject.Models.Category;
import com.example.samsproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddCategory extends AppCompatActivity {

    List Categories;
    EditText categoryName;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private String TAG = "AddCategoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        myRef = database.getReference("categories");

        categoryName = findViewById(R.id.add_category_name);

        Categories = new ArrayList();

        getSupportActionBar().setTitle("Add Category");
    }

    public void createCategory(View view) {
        String category_name = categoryName.getText().toString().trim();

        if(!TextUtils.isEmpty(category_name)){
            String id = generateId();
            Category category = new Category(id, category_name);

            createCategory(category, id);

            categoryName.setText("");
            notification();
            Toast.makeText(this, "New Category Created", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Please enter a category name.", Toast.LENGTH_SHORT).show();
        }

    }

    public String generateId(){
        return myRef.push().getKey();
    }

    public void createCategory(Category category, String id){
        myRef.child(id).setValue(category);
    }

    public void cancelCategory(View view){
        startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
    }

    public void notification(){
        Context context = AddCategory.this;

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
                .setContentTitle("Category Added")
                .setContentText("You have successfully addded category")
                .setContentIntent(pi);//helps to run in background

        nm.notify(0, notifBuilder.build());//shows the notification
    }
}
