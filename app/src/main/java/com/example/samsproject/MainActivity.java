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
import com.example.samsproject.Activities.AddRolesActivity;
import com.example.samsproject.Activities.Admin.Item;
import com.example.samsproject.Activities.HomepageActivity;
import com.example.samsproject.Activities.OrderViewActivity;
import com.example.samsproject.Activities.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle myToogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(getApplicationContext(), NavigationDrawerActivity.class));
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        mDrawerLayout = findViewById(R.id.drawer_layout);
//        myToogle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
//        mDrawerLayout.addDrawerListener(myToogle);
//        myToogle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


 //       ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
 //               R.string.navigation_drawer_open, R.string.navigation_drawer_close);

//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
 //       startActivity(new Intent(getApplicationContext(), Additem.class));
//        startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
//        setContentView(R.layout.activity_main);
//
//            drawer = findViewById(R.id.drawer);
    }

//    @Override
//    public void onBackPressed() {
//        if (drawer.isDrawerOpen(GravityCompat.START)){
//            drawer.closeDrawer(GravityCompat.START);
//        }else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(myToogle.onOptionsItemSelected(item)){
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public void userlist(View view) {
        startActivity(new Intent(getApplicationContext(), OrderViewActivity.class));
    }

    public void forgotPassword(View view) {
        
    }
}

