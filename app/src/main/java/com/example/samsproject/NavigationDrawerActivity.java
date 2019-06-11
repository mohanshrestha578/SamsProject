package com.example.samsproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.samsproject.Activities.AddRolesActivity;
import com.example.samsproject.Activities.Admin.Item;
import com.example.samsproject.Activities.CategoryActivity;
import com.example.samsproject.Activities.HomepageActivity;
import com.example.samsproject.Activities.OrderViewActivity;
import com.example.samsproject.Activities.Waiter.TableSectionActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment fragment = null;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences prefs = getSharedPreferences("user_info", MODE_PRIVATE);

        String role = prefs.getString("role", "customer");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view_customer);
//        if(role.equals("customer")){
//        }else if(role.equals("Chef")){
//            navigationView = findViewById(R.id.nav_view_chef);
//        }else if(role.equals("Waiter")){
//            navigationView = findViewById(R.id.nav_view_waiter);
//        }else{
//            navigationView = findViewById(R.id.nav_view_admin);
//        }



        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

//        navigationView.setCheckedItem(R.id.nav_menu);

        //default fragment in home page
//        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
//        ft.add(R.id.display_area, new HomepageActivity());
//        ft.commit();


//        navigationView.setCheckedItem(R.id.nav_menu);

//        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_menu));

//        Intent i = new Intent(NavigationDrawerActivity.this, HomepageActivity.class);
//        startActivity(i);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.display_area, new FragmentMenu());
        ft.commit();
    }

//    @Override
//    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        toggle.syncState();
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            toggle.setDrawerIndicatorEnabled(true);
        } else {
            super.onBackPressed();
            toggle.setDrawerIndicatorEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            Intent i = new Intent(NavigationDrawerActivity.this, HomepageActivity.class);
            startActivity(i);
           // fragment = new FragmentMenu();
        } else if (id == R.id.nav_reserve) {

            startActivity(new Intent(this, CustomerTableLists.class));

//            Intent i = new Intent(NavigationDrawerActivity.this, CustomerTableLists.class);
//            startActivity(i);

             //  fragment = new FragmentReserveTable();
        } else if (id == R.id.nav_orders) {
            Intent i = new Intent(NavigationDrawerActivity.this, TableSectionActivity.class);
            startActivity(i);
            //fragment = new FragmentOrders();
        } else if (id == R.id.nav_add_staff) {
            Intent i = new Intent(NavigationDrawerActivity.this, Addstaff.class);
            startActivity(i);
            //fragment = new FragmentAddStaff();
        } else if (id == R.id.nav_add_item) {
            Intent i = new Intent(NavigationDrawerActivity.this, Additem.class);
            startActivity(i);
            //fragment = new FragmentAddItem();
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
        } else if(id == R.id.nav_item_list){
            Intent i = new Intent(NavigationDrawerActivity.this, Item.class);
            startActivity(i);
        } else if(id == R.id.nav_add_roles){
            Intent i = new Intent(NavigationDrawerActivity.this, AddRolesActivity.class);
            startActivity(i);
        } else if(id == R.id.nav_category_lists){
            Intent i = new Intent(NavigationDrawerActivity.this, CategoryActivity.class);
            startActivity(i);
        } else if(id == R.id.nav_cart_items){
            Intent i = new Intent(NavigationDrawerActivity.this, OrderViewActivity.class);
            startActivity(i);
        } else if(id == R.id.nav_logout){
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(NavigationDrawerActivity.this, Login.class);
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
