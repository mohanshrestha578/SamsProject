package com.example.samsproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.samsproject.Models.ModelItem;
import com.example.samsproject.Models.Order;
import com.example.samsproject.RecyclerViews.RecyclerViewRecommend;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ItemViewActivity extends AppCompatActivity {

    private static final String TAG = "HomepageActivity";

    private ArrayList<ModelItem> mItemDetails = new ArrayList<>();

    private ImageView itemImage;
    private TextView itemTitle;
    private TextView itemCategory;
    private TextView itemPrice;
    private TextView itemDescription;
    private Button addToCartBtn;

    private String item_id;
    private String extra_url;

    private DatabaseReference dbR;

    Integer item_dis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        itemTitle = findViewById(R.id.item_view_name);
        itemImage = findViewById(R.id.item_view_image);
        itemCategory = findViewById(R.id.item_view_category);
        itemPrice = findViewById(R.id.item_view_price);
        itemDescription = findViewById(R.id.item_view_description);
        addToCartBtn = findViewById(R.id.addToCart);

        Intent intent = getIntent();

        item_id = intent.getStringExtra("id");
        itemTitle.setText(intent.getStringExtra("name"));
        itemDescription.setText(intent.getStringExtra("description"));

        Glide.with(this)
                .load(intent.getStringExtra("imageUrl"))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(itemImage);
        extra_url = intent.getStringExtra("imageUrl");

        item_dis = intent.getIntExtra("discount", 0);

        itemCategory.setText(intent.getStringExtra("category_name"));
        itemPrice.setText(concatString("Rs.", intent.getStringExtra("price"), false));

        getRecommendDish();
    }

    private void getRecommendDish(){

        Log.d(TAG, "getRecommendDish: ");

        dbR = FirebaseDatabase.getInstance().getReference();

        Query query = dbR.child("Items").orderByChild("category_name").equalTo(itemCategory.getText().toString());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    ModelItem item = snapshot.getValue(ModelItem.class);
                    if(!item.getName().equals(itemTitle.getText().toString())){
                        mItemDetails.add(item);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        dbR.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                offer_items.clear();
//
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    ModelItem item = postSnapshot.getValue(ModelItem.class);
//                    if(item.getPlacement().equals("Offers") || item.getDiscount() > 0){
//                        offer_items.add(item);
//                    }
//                }
//
//                initRecyclerViewOffers();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//                endLoader(progress);
//            }
//        });

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

        RecyclerViewRecommend viewItem = new RecyclerViewRecommend(this, mItemDetails);
        recyclerView.setAdapter(viewItem);
    }

    public void addToCart(View view) {

        showQuantityDialog();

    }

    public void showQuantityDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = LayoutInflater.from(this);
        final View dialogView = inflater.inflate(R.layout.layout_item_quantity, null);
        dialogBuilder.setView(dialogView);

        final EditText item_quantity = dialogView.findViewById(R.id.item_quantity);
        final Context context = this;

        dialogBuilder.setTitle("Food Quantity");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        Button submit_btn = dialogView.findViewById(R.id.item_quantity_submitBtn);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String price_str = itemPrice.getText().toString().substring(3);
                Integer price_int = Integer.parseInt(price_str);

                if(TextUtils.isEmpty(item_quantity.getText().toString())){
                    Toast.makeText(context, "Food quantity must not be null.", Toast.LENGTH_LONG).show();
                }else{

                    Integer quantity = Integer.parseInt(item_quantity.getText().toString());

                    if(quantity != null){
                        if(quantity < 1){
                            Toast.makeText(context, "Food quantity must be atleast 1.", Toast.LENGTH_LONG).show();
                        }else{

                            DatabaseReference orderDb = FirebaseDatabase.getInstance().getReference("Orders");

                            String id = orderDb.push().getKey();

                            String user_id = "";
                            String username = "";

                            SharedPreferences prefs = getSharedPreferences("user_info", MODE_PRIVATE);
                            String restoredText = prefs.getString("uuid", null);
                            if (restoredText != null) {
                                user_id = prefs.getString("uuid", "no id");
                                username = prefs.getString("username", "Angnima");
                            }

                            Order order = new Order(id,
                                    user_id,
                                    username,
                                    item_id,
                                    itemTitle.getText().toString(),
                                    price_int,
                                    quantity,
                                    extra_url,
                                    item_dis,
                                    "cart added",
                                    ""
                            );

                            orderDb.child(id).setValue(order);

                            Toast.makeText(context, "Order Successfully Placed!", Toast.LENGTH_LONG).show();
                            b.cancel();

                        }
                    }

                }

            }
        });

    }


}
