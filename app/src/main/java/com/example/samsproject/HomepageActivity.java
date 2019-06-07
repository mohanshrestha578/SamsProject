package com.example.samsproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    private DatabaseReference dbR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        getImages();
        getOffers();

        dbR = FirebaseDatabase.getInstance().getReference("Table Information");

    }

    private void getImages(){
        Log.d(TAG, "initImageBitmaps: ");

        mTitle.add("Mo:mo");
        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6c/Plateful_of_Momo_in_Nepal.jpg/220px-Plateful_of_Momo_in_Nepal.jpg");
        mPrice.add(
                concatString("Rs.","120", false)
        );

        mTitle.add("Chowmein");
        mImageUrls.add("https://www.jessicagavin.com/wp-content/uploads/2018/01/chow-mein-1200.jpg");
        mPrice.add(
                concatString("Rs.","100", false)
        );

        mTitle.add("Sausage");
        mImageUrls.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRV78P93wZVTQ71xUJVYU7Q1dsCGek6bn7tWN3cELn4UL8VLvq7");
        mPrice.add(
                concatString("Rs.","60", false)
        );

        mTitle.add("Gintama");
        mImageUrls.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSBmsLfV7n_xeAnGcHOnQ_-qzk7wZ0smX460wOL8NSlHExKPmJ3Pw");
        mPrice.add(
                concatString("Rs.","80", false)
        );

        mTitle.add("Fried Rice");
        mImageUrls.add("https://www.fifteenspatulas.com/wp-content/uploads/2012/03/Fried-Rice-Fifteen-Spatulas-8-640x427.jpg");
        mPrice.add(
                concatString("Rs.","120", false)
        );

        initRecyclerViewItem();
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

        offer_title.add("Snacks Combo King");
        offer_price.add(
                concatString("Rs.","900", true)
        );
        offer_discount.add(
                concatString("15%","off", true)
        );
        offer_category.add("Snacks");
        offer_image.add("https://www.rd.com/wp-content/uploads/2018/12/shutterstock_1161597079-760x506.jpg");

        offer_title.add("Family Desert Special");
        offer_price.add(
                concatString("Rs.","525", true)
        );
        offer_discount.add(
                concatString("20%","Off", true)
        );
        offer_category.add("Desserts");
        offer_image.add("https://www.ndtv.com/cooks/images/stuffed-masala-mushrooms_article.jpg");

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
    }

}
