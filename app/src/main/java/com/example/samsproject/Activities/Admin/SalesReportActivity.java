package com.example.samsproject.Activities.Admin;

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
import android.widget.Toast;

import com.example.samsproject.Models.ModelItem;
import com.example.samsproject.Models.Order;
import com.example.samsproject.R;
import com.example.samsproject.RecyclerViews.Admin.RecyclerViewSales;
import com.example.samsproject.Reservation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SalesReportActivity extends AppCompatActivity {

    private static final String TAG = "OrderViewActivity";

    private DatabaseReference dbRef;
    private List<Order> orders = new ArrayList<>();

    private Context context;

    ProgressDialog progress;

    int final_amount = 0;
    int final_discount = 0;
    int total_amount = 0;

    private TextView order_total_amt;
    private TextView order_final_amt;
    private TextView order_final_dis;

    List<String> order_id = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_report);

        dbRef = FirebaseDatabase.getInstance().getReference("Orders");

        progress = new ProgressDialog(this);

        order_total_amt = findViewById(R.id.sales_list_total_amount);
        order_final_amt = findViewById(R.id.sales_list_final_amount);
        order_final_dis = findViewById(R.id.sales_list_total_discount);

        context = this;

        getDbOrders();
    }


    private void getDbOrders() {

        startLoader(progress);

        Log.d(TAG, "initImageBitmaps: ");

        Query orderQuery = FirebaseDatabase.getInstance().getReference("Orders")
                .orderByChild("status").equalTo("order received");
        orderQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orders.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Order item = postSnapshot.getValue(Order.class);
                    orders.add(item);

                    int tot_amt = item.getItem_price() * item.getQuantity();
                    total_amount += tot_amt;

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
                initRecyclerViewSales();
                order_total_amt.setText("Rs." + total_amount);
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

    private void initRecyclerViewSales() {
        Log.d(TAG, "initRecyclerViewSales: from SalesReportActivity");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerView = findViewById(R.id.sales_report_rv);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerViewSales viewItem = new RecyclerViewSales(this, orders);
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
