package com.example.samsproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
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
import com.example.samsproject.Activities.Admin.SalesReportActivity;
import com.example.samsproject.Activities.CategoryActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AdminDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment fragment = null;
    Context context = this;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("Admin");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.screen_area_admin, new FragmentMenu());
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Sign Out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(context, Login.class));
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doubleBackToExitPressedOnce = false;
            }
        }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_drawer, menu);
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

        if (id == R.id.nav_add_staff) {
            Intent i = new Intent(AdminDrawer.this, Addstaff.class);
            startActivity(i);
            //fragment = new FragmentAddStaff();
        } else if (id == R.id.nav_add_item) {
            Intent i = new Intent(AdminDrawer.this, Additem.class);
            startActivity(i);
            //fragment = new FragmentAddItem();
        } else if(id == R.id.nav_staff_list){
            Intent i = new Intent(AdminDrawer.this, AdminStaffLists.class);
            startActivity(i);
        } else if (id == R.id.nav_add_category){
            Intent i = new Intent(AdminDrawer.this, AddCategory.class);
            startActivity(i);
        } else if (id == R.id.nav_table_lists){
            Intent i = new Intent(AdminDrawer.this, AdminTableLists.class);
            startActivity(i);
        } else if (id == R.id.nav_create_table){
            Intent i = new Intent(AdminDrawer.this, CreateTable.class);
            startActivity(i);
        } else if(id == R.id.nav_item_list){
            Intent i = new Intent(AdminDrawer.this, Item.class);
            startActivity(i);
        } else if(id == R.id.nav_add_roles){
            Intent i = new Intent(AdminDrawer.this, AddRolesActivity.class);
            startActivity(i);
        } else if(id == R.id.nav_category_lists){
            Intent i = new Intent(AdminDrawer.this, CategoryActivity.class);
            startActivity(i);
        } else if(id == R.id.nav_generate_report){
            Intent i = new Intent(AdminDrawer.this, SalesReportActivity.class);
            startActivity(i);
        }
        else if(id == R.id.nav_logout){
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(AdminDrawer.this, Login.class);
            startActivity(i);
        }



        if(fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();

            ft.replace(R.id.screen_area_admin, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
