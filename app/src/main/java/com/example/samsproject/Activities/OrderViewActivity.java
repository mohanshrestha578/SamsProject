package com.example.samsproject.Activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.samsproject.Models.Order;
import com.example.samsproject.R;
import com.example.samsproject.RecyclerViews.RecyclerViewOrders;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderViewActivity extends AppCompatActivity {

    private static final String TAG = "OrderViewActivity";

    private DatabaseReference dbRef;
    private ArrayList<Order> orders = new ArrayList<>();

    ProgressDialog progress;

    int final_amount = 0;
    int final_discount = 0;

    private TextView order_final_amt;
    private TextView order_final_dis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_view);

        dbRef = FirebaseDatabase.getInstance().getReference("Orders");

        progress = new ProgressDialog(this);

        order_final_amt = findViewById(R.id.order_list_final_amount);
        order_final_dis = findViewById(R.id.order_list_total_discount);

        getDbOrders();
    }

    private void getDbOrders() {

        startLoader(progress);

        Log.d(TAG, "initImageBitmaps: ");

        SharedPreferences prefs = getSharedPreferences("user_info", MODE_PRIVATE);
        String restoredText = prefs.getString("uuid", null);

        if(restoredText != null){
            Query orderQuery = FirebaseDatabase.getInstance().getReference("Orders")
                    .orderByChild("user_id").equalTo(restoredText);
            orderQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    orders.clear();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Order item = postSnapshot.getValue(Order.class);
                        orders.add(item);
                        if(item.getDiscount() > 0){
                            int amt = (item.getItem_price() - ((item.getItem_price() * item.getDiscount())/100)) * item.getQuantity() ;
                            int dis = ((item.getItem_price() * item.getDiscount())/100) * item.getQuantity();
                            final_amount += amt;
                            final_discount += dis;
                        }else{
                            int amt = (item.getItem_price() * item.getQuantity());
                            final_amount += amt;
                        }
                    }

                    initRecyclerViewOrderItem();
                    order_final_amt.setText("Rs." + final_amount);
                    order_final_dis.setText("Rs." + final_discount);
                    endLoader(progress);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    endLoader(progress);
                }
            });
        }

    }

    private void initRecyclerViewOrderItem() {
        Log.d(TAG, "initRecyclerViewOrderItem: from OrderViewActivity");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerView = findViewById(R.id.order_view_rv);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerViewOrders viewItem = new RecyclerViewOrders(this, orders);
        recyclerView.setAdapter(viewItem);
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
