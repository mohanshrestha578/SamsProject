package com.example.samsproject.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.samsproject.Models.Category;
import com.example.samsproject.Models.ModelItem;
import com.example.samsproject.Models.User;
import com.example.samsproject.R;
import com.example.samsproject.RecyclerViews.RecyclerViewItem;
import com.example.samsproject.RecyclerViews.RecyclerViewOffers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomepageActivity extends AppCompatActivity {

    private static final String TAG = "HomepageActivity";

    private ArrayList<ModelItem> mItemDetails = new ArrayList<>();

    private ArrayList<ModelItem> offer_items = new ArrayList<>();

    ProgressDialog progress;

    private DatabaseReference dbR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        progress = new ProgressDialog(this);

        dbR = FirebaseDatabase.getInstance().getReference("Items");

        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences prefs = getSharedPreferences("user_info", MODE_PRIVATE);
        String restoredText = prefs.getString("uuid", null);

        if(restoredText == null){
            setAllUserData(fUser.getUid());
        }

        getImages();
        getOffers();


    }

    private void setAllUserData(final String restoredText) {
        final Context c = this;
        Query dbUser = FirebaseDatabase.getInstance()
                .getReference("users").orderByChild("uuid").equalTo(restoredText);
        dbUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    User user = postSnapshot.getValue(User.class);

                    if(!user.getId().isEmpty()){
                        SharedPreferences.Editor editor = getSharedPreferences("user_info", MODE_PRIVATE).edit();
                        editor.putString("id", user.getId());
                        editor.putString("uuid", restoredText);
                        editor.putString("username", user.getName());
                        editor.apply();

                        Toast.makeText(c, restoredText, Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getImages(){
        startLoader(progress);

        Log.d(TAG, "initImageBitmaps: ");

        dbR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                mItemDetails.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ModelItem item = postSnapshot.getValue(ModelItem.class);
                    if(item.getPlacement().equals("Our Special")){
                        mItemDetails.add(item);
                    }
                }

                initRecyclerViewItem();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                endLoader(progress);
            }
        });

    }

    private void initRecyclerViewItem(){
        Log.d(TAG, "initRecyclerViewItem: from HomepageActivity");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerView = findViewById(R.id.homepage_rv_special);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewItem viewItem = new RecyclerViewItem(this, mItemDetails);
        recyclerView.setAdapter(viewItem);
    }

    private void getOffers(){

        Log.d(TAG, "getOffers: ");

        dbR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                offer_items.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ModelItem item = postSnapshot.getValue(ModelItem.class);
                    if(item.getPlacement().equals("Offers") || item.getDiscount() > 0){
                        offer_items.add(item);
                    }
                }

                initRecyclerViewOffers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                endLoader(progress);
            }
        });

        initRecyclerViewOffers();
    }

    private String concatString(String value1, String value2, Boolean spaceInBetween){
        if(spaceInBetween){
            return value1 + " " + value2;
        }
        return value1 + value2;
    }

    private void initRecyclerViewOffers(){
        Log.d(TAG, "initRecyclerViewOffers: from HomepageActivity");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerView = findViewById(R.id.homepage_rv_offers);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewOffers viewItem = new RecyclerViewOffers(offer_items, this);
        recyclerView.setAdapter(viewItem);

        endLoader(progress);
    }

    public void startLoader(ProgressDialog progress){
        progress.setMessage("Loading Data...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }

    public void endLoader(ProgressDialog progress){
        progress.dismiss();
    }

}
