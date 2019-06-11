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
        startActivity(new Intent(getApplicationContext(), NavigationDrawerActivity  .class));
        setContentView(R.layout.activity_main);

    }

}

