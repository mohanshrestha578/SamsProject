package com.example.samsproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.GravityCompat;
import android.view.View;

import com.example.samsproject.Activities.AddCategory;
import com.example.samsproject.Activities.Admin.Item;
import com.example.samsproject.Activities.HomepageActivity;
import com.example.samsproject.Activities.OrderViewActivity;
import com.example.samsproject.Activities.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;

    private ActionBarDrawerToggle myToogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main_layout);
        startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
 //       startActivity(new Intent(getApplicationContext(), Additem.class));
//        startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
//        setContentView(R.layout.activity_main);
//
//            drawer = findViewById(R.id.drawer);
//
//            myToogle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
//
//            drawer.addDrawerListener(myToogle);
//            myToogle.syncState();
//
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(myToogle.onOptionsItemSelected(item)){
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public void userlist(View view) {
        startActivity(new Intent(getApplicationContext(), Addstaff.class));
    }

    public void forgotPassword(View view) {
        
    }
}

