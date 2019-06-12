package com.example.samsproject.RecyclerViews.Admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.samsproject.Models.Order;
import com.example.samsproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewSales extends RecyclerView.Adapter<RecyclerViewSales.ViewHolder>{


    private static final String TAG = "RecyclerViewSales";
    private Context context;
    private List<Order> mOrders;


    public RecyclerViewSales(Context context, List<Order> mOrders) {
        this.context = context;
        this.mOrders = mOrders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_sales_item_ist, viewGroup, false);
        return new RecyclerViewSales.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: ");

        Order data = mOrders.get(i);

        viewHolder.order_name.setText(data.getItem_name());
        viewHolder.order_price.setText("Rs." + data.getItem_price());
        viewHolder.order_quantity.setText(data.getQuantity().toString());
        if(data.getDiscount() == 0){
            viewHolder.order_discount.setText("");
            viewHolder.order_discount_amt.setText("Rs. 0");
        }else{
            viewHolder.order_discount.setText(data.getDiscount().toString() + "% Off");
        }

        int amt = data.getItem_price() * data.getQuantity();

        if(data.getDiscount() > 0){
            Integer dis = data.getDiscount();
            int tot = amt - ((amt * dis) / 100);

            viewHolder.order_discount_amt.setText("Rs." + ((amt*dis)/100));

            viewHolder.order_total_amt.setText("Rs." + tot);
        }else{
            viewHolder.order_total_amt.setText("Rs." + amt);
        }

    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView order_name;
        TextView order_price;
        TextView order_quantity;
        TextView order_discount;
        TextView order_discount_amt;
        TextView order_total_amt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            order_name = itemView.findViewById(R.id.sales_view_name);
            order_price = itemView.findViewById(R.id.sales_view_price);
            order_quantity = itemView.findViewById(R.id.sales_view_quantity);
            order_discount = itemView.findViewById(R.id.sales_view_discount);
            order_discount_amt = itemView.findViewById(R.id.sales_view_disc_amt);
            order_total_amt = itemView.findViewById(R.id.sales_view_total_amt);
        }
    }

}
