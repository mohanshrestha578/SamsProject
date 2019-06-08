package com.example.samsproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samsproject.RecyclerViews.RecyclerViewRecommend;

import java.util.ArrayList;

public class ItemViewActivity extends AppCompatActivity {

    private static final String TAG = "HomepageActivity";

    private ArrayList<String> mTitle = new ArrayList<>();
    private ArrayList<String> mPrice = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    private ImageView itemImage;
    private TextView itemTitle;
    private TextView itemCategory;
    private TextView itemPrice;
    private TextView itemDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        itemTitle = findViewById(R.id.item_view_name);
        itemImage = findViewById(R.id.item_view_image);
        itemCategory = findViewById(R.id.item_view_category);
        itemPrice = findViewById(R.id.item_view_price);
        itemDescription = findViewById(R.id.item_view_description);

        itemTitle.setText("The Squidish Pizza");
        itemDescription.setText("Pizza cheese frequently consists of a blend of two or more cheeses, " +
                "such as low-moisture Mozzarella or Provolone. Low-moisture Mozzarella was first " +
                "manufactured in dairy factories in the Midwestern United States, and was originally called " +
                "'pizza cheese'.");

        itemImage.setImageResource(R.drawable.food_banner);
        itemCategory.setText("Pizza");
        itemPrice.setText("Rs.450");

        getRecommendDish();
    }

    private void getRecommendDish(){

        Log.d(TAG, "getRecommendDish: ");

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

        initGetRecommendDish();

    }

    private String concatString(String value1, String value2, Boolean spaceInBetween){
        if(spaceInBetween){
            return value1 + " " + value2;
        }
        return value1 + value2;
    }

    private void initGetRecommendDish(){
        Log.d(TAG, "initGetItemDetails: from ItemViewActivity");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recommended_dish);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerViewRecommend viewItem = new RecyclerViewRecommend(this, mTitle, mImageUrls, mPrice);
        recyclerView.setAdapter(viewItem);
    }

}
