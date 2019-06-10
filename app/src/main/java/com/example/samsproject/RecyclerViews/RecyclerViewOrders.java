package com.example.samsproject.RecyclerViews;

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

public class RecyclerViewOrders extends RecyclerView.Adapter<RecyclerViewOrders.ViewHolder> {

    private static final String TAG = "RecyclerViewOrders";
    private Context context;
    private List<Order> mOrders = new ArrayList<>();


    public RecyclerViewOrders(Context context, ArrayList<Order> mOrders) {
        this.context = context;
        this.mOrders = mOrders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_order_item_list, viewGroup, false);
        return new RecyclerViewOrders.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: ");

        final int pos = i;

        Order data = mOrders.get(i);

        Glide.with(context)
                .asBitmap()
                .load(data.getImage())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(viewHolder.image);

        viewHolder.order_name.setText(data.getItem_name());
        viewHolder.order_price.setText("Rs." + data.getItem_price());
        viewHolder.order_quantity.setText(data.getQuantity().toString());
        if(data.getDiscount() == 0){
            viewHolder.order_discount.setText("");
        }else{
            viewHolder.order_discount.setText(data.getDiscount().toString() + "% Off");
        }

        int amt = data.getItem_price() * data.getQuantity();

        if(data.getDiscount() > 0){
            Integer dis = data.getDiscount();
            int tot = amt - ((amt * dis) / 100);

            viewHolder.order_total_amt.setText("Rs." + tot);
        }else{
            viewHolder.order_total_amt.setText("Rs." + amt);
        }


        viewHolder.order_delete_btn.setOnClickListener(new View.OnClickListener() {

            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Orders").child(mOrders.get(pos).getId());

            @Override
            public void onClick(View v) {

                dbRef.removeValue();
                Toast.makeText(context,"Order Deleted Successfully!", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView order_name;
        TextView order_price;
        TextView order_quantity;
        TextView order_discount;
        TextView order_total_amt;
        TextView order_total_discount;
        TextView order_final_amount;
        Button order_delete_btn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.order_view_image);
            order_name = itemView.findViewById(R.id.order_view_name);
            order_price = itemView.findViewById(R.id.order_view_price);
            order_quantity = itemView.findViewById(R.id.order_view_quantity);
            order_discount = itemView.findViewById(R.id.order_view_discount);
            order_total_amt = itemView.findViewById(R.id.order_view_total_amt);
            order_delete_btn = itemView.findViewById(R.id.order_view_delBtn);
        }
    }

}
