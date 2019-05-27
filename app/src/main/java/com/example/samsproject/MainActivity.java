package com.example.samsproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;

    private ActionBarDrawerToggle myToogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        startActivity(new Intent(getApplicationContext(), Login.class));
        startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
//        setContentView(R.layout.activity_main);
//
//        drawer = findViewById(R.id.drawer);
//
//        myToogle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
//
//        drawer.addDrawerListener(myToogle);
//        myToogle.syncState();
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(myToogle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void userlist(View view) {

        startActivity(new Intent(getApplicationContext(), Additem.class));
//        startActivity(new Intent(getApplicationContext(), UsersList.class));
    }

    public void forgotPassword(View view) {
        
    }
}

