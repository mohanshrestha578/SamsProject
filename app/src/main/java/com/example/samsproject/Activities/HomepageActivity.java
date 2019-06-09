package com.example.samsproject.Activities;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.samsproject.Models.Category;
import com.example.samsproject.Models.ModelItem;
import com.example.samsproject.R;
import com.example.samsproject.RecyclerViews.RecyclerViewItem;
import com.example.samsproject.RecyclerViews.RecyclerViewOffers;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomepageActivity extends AppCompatActivity {

    private static final String TAG = "HomepageActivity";

    private ArrayList<String> mTitle = new ArrayList<>();
    private ArrayList<String> mPrice = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    private ArrayList<String> offer_image = new ArrayList<>();
    private ArrayList<String> offer_title = new ArrayList<>();
    private ArrayList<String> offer_price = new ArrayList<>();
    private ArrayList<String> offer_discount = new ArrayList<>();
    private ArrayList<String> offer_category = new ArrayList<>();

    ProgressDialog progress;

    private DatabaseReference dbR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        progress = new ProgressDialog(this);

        dbR = FirebaseDatabase.getInstance().getReference("Items");

        startLoader(progress);

        getImages();
        getOffers();


    }

    private void getImages(){
        Log.d(TAG, "initImageBitmaps: ");

        dbR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                mTitle.clear();
                mPrice.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ModelItem item = postSnapshot.getValue(ModelItem.class);
                    mTitle.add(item.getName());
                    mPrice.add(item.getPrice());
                    mImageUrls.add(item.getImageUrl());
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
        RecyclerViewItem viewItem = new RecyclerViewItem(this, mTitle, mImageUrls, mPrice);
        recyclerView.setAdapter(viewItem);
    }

    private void getOffers(){

        Log.d(TAG, "getOffers: ");

        dbR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                offer_title.clear();
                offer_price.clear();
                offer_discount.clear();
                offer_category.clear();
                offer_image.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ModelItem item = postSnapshot.getValue(ModelItem.class);
                    offer_title.add(item.getName());
                    offer_price.add(item.getPrice());
                    offer_discount.add("0");
                    offer_category.add(item.getCategory_name());
                    offer_image.add(item.getImageUrl());
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
        RecyclerViewOffers viewItem = new RecyclerViewOffers(offer_title, offer_image, offer_price, offer_discount, offer_category, this);
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
