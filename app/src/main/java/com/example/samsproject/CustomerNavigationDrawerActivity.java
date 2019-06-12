package com.example.samsproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
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

public class CustomerNavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment fragment = null;
    Context context = this;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_activity_navigation_drawer);
        Toolbar toolbar = findViewById(R.id.customer_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.customer_drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view_customer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.customer_display_area, new FragmentMenu());
        ft.commit();


        getSupportActionBar().setTitle("Customer");

//        navigationView.setCheckedItem(R.id.nav_menu);

        //default fragment in home page
//        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
//        ft.add(R.id.display_area, new HomepageActivity());
//        ft.commit();


//        navigationView.setCheckedItem(R.id.nav_menu);

//        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_menu));

//        Intent i = new Intent(CustomerNavigationDrawerActivity.this, HomepageActivity.class);
//        startActivity(i);


    }

//    @Override
//    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        toggle.syncState();
//    }

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
            Intent i = new Intent(CustomerNavigationDrawerActivity.this, HomepageActivity.class);
            startActivity(i);
           // fragment = new FragmentMenu();
        } else if (id == R.id.nav_reserve) {

            startActivity(new Intent(this, CustomerTableLists.class));

//            Intent i = new Intent(CustomerNavigationDrawerActivity.this, CustomerTableLists.class);
//            startActivity(i);

             //  fragment = new FragmentReserveTable();
        } else if(id == R.id.nav_cart_items){
            Intent i = new Intent(CustomerNavigationDrawerActivity.this, OrderViewActivity.class);
            startActivity(i);
        } else if(id == R.id.nav_add_item){
            Intent i = new Intent(CustomerNavigationDrawerActivity.this, Additem.class);
            startActivity(i);
        }
        else if(id == R.id.nav_logout){
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(CustomerNavigationDrawerActivity.this, Login.class);
            startActivity(i);
        }


        if(fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();

            ft.replace(R.id.customer_display_area, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.customer_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
