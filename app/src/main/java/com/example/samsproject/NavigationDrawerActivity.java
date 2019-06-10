package com.example.samsproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.samsproject.Activities.AddCategory;
import com.example.samsproject.Activities.HomepageActivity;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_menu);

        //default fragment in home page
//        Intent i = new Intent(NavigationDrawerActivity.this, HomepageActivity.class);
//        startActivity(i);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.display_area, new FragmentMenu());
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            Intent i = new Intent(NavigationDrawerActivity.this, HomepageActivity.class);
            startActivity(i);
            //fragment = new FragmentMenu();
        } else if (id == R.id.nav_reserve) {
            Intent i = new Intent(NavigationDrawerActivity.this, CustomerTableLists.class);
            startActivity(i);
            //fragment = new FragmentReserveTable();
        } else if (id == R.id.nav_orders) {
            Intent i = new Intent(NavigationDrawerActivity.this, CreateReservation.class);
            startActivity(i);
            //fragment = new FragmentOrders();
        } else if (id == R.id.nav_add_staff) {
            Intent i = new Intent(NavigationDrawerActivity.this, Addstaff.class);
            startActivity(i);
            //fragment = new FragmentAddStaff();
        } else if (id == R.id.nav_userslist) {
            Intent i = new Intent(NavigationDrawerActivity.this, CreateReservation.class);
            startActivity(i);
            //fragment = new FragmentUserList();
        } else if (id == R.id.nav_add_item) {
            Intent i = new Intent(NavigationDrawerActivity.this, Additem.class);
            startActivity(i);
            //fragment = new FragmentAddItem();
        } else if(id == R.id.nav_generate_report){
            Intent i = new Intent(NavigationDrawerActivity.this, CreateReservation.class);
            startActivity(i);
            //fragment = new FragmentGenerateReport();
        } else if(id == R.id.nav_staff_list){
            Intent i = new Intent(NavigationDrawerActivity.this, AdminStaffLists.class);
            startActivity(i);
        } else if (id == R.id.nav_add_category){
            Intent i = new Intent(NavigationDrawerActivity.this, AddCategory.class);
            startActivity(i);
        } else if (id == R.id.nav_table_lists){
            Intent i = new Intent(NavigationDrawerActivity.this, AdminTableLists.class);
            startActivity(i);
        } else if (id == R.id.nav_create_table){
            Intent i = new Intent(NavigationDrawerActivity.this, CreateTable.class);
            startActivity(i);
        }

        if(fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();

            ft.replace(R.id.display_area, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
