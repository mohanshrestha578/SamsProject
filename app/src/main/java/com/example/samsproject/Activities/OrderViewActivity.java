package com.example.samsproject.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.samsproject.CreateReservation;
import com.example.samsproject.Models.Order;
import com.example.samsproject.R;
import com.example.samsproject.RecyclerViews.RecyclerViewOrders;
import com.example.samsproject.Reservation;
import com.example.samsproject.Table;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderViewActivity extends AppCompatActivity {

    private static final String TAG = "OrderViewActivity";

    private DatabaseReference dbRef;
    private ArrayList<Order> orders = new ArrayList<>();

    private Context context;

    ProgressDialog progress;

    int final_amount = 0;
    int final_discount = 0;

    private TextView order_final_amt;
    private TextView order_final_dis;

    List<String> order_id = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_view);

        dbRef = FirebaseDatabase.getInstance().getReference("Orders");

        progress = new ProgressDialog(this);

        order_final_amt = findViewById(R.id.order_list_final_amount);
        order_final_dis = findViewById(R.id.order_list_total_discount);

        context = this;

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
        endLoader(progress);
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


    public void placeOrder(View view) {
        showDialog();
    }

    private void showDialog() {
        //spinner add table reservation
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.dialog_reservation_table, null);
        dialogBuilder.setView(dialogView);

        final TextView table_num = findViewById(R.id.tableNumb);
        Button reserve_btn = findViewById(R.id.reserveButton);

        reserve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, CreateReservation.class));
            }
        });

        SharedPreferences prefs = getSharedPreferences("user_info", MODE_PRIVATE);
        String restoredText = prefs.getString("uuid", null);

        final String uuid = restoredText;


        Button confirm_btn = findViewById(R.id.confirmButton);
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Query query = FirebaseDatabase.getInstance().getReference("Orders").orderByChild("user_id").equalTo(uuid);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                            Order order = postSnapshot.getValue(Order.class);
                            order_id.add(order.getId());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Orders");
                HashMap hashMap = new HashMap();

                hashMap.put("tableNumber", table_num.getText().toString());
                hashMap.put("status", "order placed");

                for(int i = 0; i < order_id.size(); i++){
                    myref.child(order_id.get(i)).updateChildren(hashMap);
                }

            }
        });



        Query query = FirebaseDatabase.getInstance().getReference("Reservation Details").orderByChild("user_id").equalTo(restoredText);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Reservation reservation = postSnapshot.getValue(Reservation.class);
                    table_num.setText(reservation.getTableNumber());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(table_num.getText().toString().equals(null)){
            reserve_btn.setVisibility(View.GONE);
        }else{
            reserve_btn.setVisibility(View.VISIBLE);
        }
    }
}
