package com.example.samsproject.Activities.Waiter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsproject.Models.ModelItem;
import com.example.samsproject.Models.Order;
import com.example.samsproject.R;
import com.example.samsproject.RecyclerViews.Waiter.WaiterOrderListRecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ItemsForOrderActivity extends AppCompatActivity {

    private static final String TAG = "ItemsForOrderActivity";
    private List<Order> orders;
    private DatabaseReference dbRef;

    private Button add_btn;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_for_order);

        progress = new ProgressDialog(this);
        orders = new ArrayList<>();

        add_btn = findViewById(R.id.waiter_order_items_addBtn);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });

        getOrderedItems();


    }

    private void showAddDialog() {

        Intent getInt = getIntent();

        Intent intent = new Intent(this, MenuListActivity.class);
        intent.putExtra("table_number", getInt.getIntExtra("table_number", 0));
        startActivity(intent);
    }

    private void getOrderedItems() {

        startLoader(progress);

        Query query = FirebaseDatabase.getInstance().getReference("Orders").orderByChild("staff_order").equalTo(1);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orders.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Order item = postSnapshot.getValue(Order.class);
                    System.out.println(item.getItem_name());
                    orders.add(item);
                }
                endLoader(progress);
                initRecyclerViewOrderList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                endLoader(progress);
            }
        });

    }

    private void initRecyclerViewOrderList() {
        Log.d(TAG, "initRecyclerViewOrderList: from ItemsForOrderActivity");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerView = findViewById(R.id.waiter_order_items_rv);
        recyclerView.setLayoutManager(layoutManager);
        WaiterOrderListRecyclerView viewItem = new WaiterOrderListRecyclerView(this, orders);
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
