package com.example.samsproject.RecyclerViews.Waiter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.List;

public class WaiterOrderListRecyclerView extends RecyclerView.Adapter<WaiterOrderListRecyclerView.ViewHolder>{

    private static final String TAG = "WaiterOrderListRecycler";
    private Context mContext;
    private List<Order> mOrders;

    public WaiterOrderListRecyclerView(Context mContext, List<Order> mOrders) {
        this.mContext = mContext;
        this.mOrders = mOrders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.waiter_order_items_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Log.d(TAG, "onBindViewHolder: ");

        final Order order = mOrders.get(i);

        viewHolder.item_name.setText(order.getItem_name());

        viewHolder.sn.setText((i+1) + ".");

        viewHolder.item_qty.setText(order.getQuantity()+" ");

        viewHolder.del_btn.setOnClickListener(new View.OnClickListener() {

            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Orders").child(order.getId());

            @Override
            public void onClick(View v) {

                dbRef.removeValue();
                Toast.makeText(mContext,"Order Deleted Successfully!", Toast.LENGTH_LONG).show();
            }
        });

        viewHolder.edit_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showUpdateDialog(order.getId(), order.getQuantity(), order.getDiscount(), order.getImage(), order.getItem_price(), order.getItem_name());
            }
        });

    }

    private void showOrderMenuDialog() {


    }

    private void showUpdateDialog(final String id, Integer quantity, Integer discount, String image, Integer item_price, String name) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View dialogView = inflater.inflate(R.layout.dialog_item_list, null);
        dialogBuilder.setView(dialogView);

        final TextView editTextName = (TextView) dialogView.findViewById(R.id.dialog_item_list_name);
        final TextView editPrice = (TextView) dialogView.findViewById(R.id.dialog_item_list_price);
        final TextView editDiscount = dialogView.findViewById(R.id.dialog_item_list_discount);
        final EditText editQuantity = dialogView.findViewById(R.id.dialog_item_list_quantity);
        final ImageView editImage = dialogView.findViewById(R.id.dialog_item_list_choose_image);

        Glide.with(mContext)
                .load(image)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(editImage);

        editTextName.setText(name);
        editPrice.setText("Rs." + item_price);
        editDiscount.setText(discount + "%");
        editQuantity.setText("" + quantity);

        final Button updateBtn = dialogView.findViewById(R.id.dialog_item_list_updateBtn);
        final Button cancleBtn = dialogView.findViewById(R.id.dialog_item_list_cancle_btn);

        dialogBuilder.setTitle("Update Food Quantity");
        final AlertDialog b = dialogBuilder.create();

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(editQuantity.getText().toString()) > 0){
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Orders").child(id);
                    HashMap map = new HashMap();
                    map.put("quantity", Integer.parseInt(editQuantity.getText().toString()));
                    dbRef.updateChildren(map);
                    Toast.makeText(mContext, "Quantity Updated Successfully!", Toast.LENGTH_LONG).show();
                    b.cancel();
                }
            }
        });

        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.cancel();
            }
        });


        b.show();

    }


    @Override
    public int getItemCount() {
        return (mOrders.size() > 0) ? mOrders.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView sn;
        TextView item_name;
        TextView item_qty;

        Button edit_btn;
        Button del_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sn = itemView.findViewById(R.id.waiter_order_items_sn);
            item_name = itemView.findViewById(R.id.waiter_order_items_name);
            item_qty = itemView.findViewById(R.id.waiter_order_items_qty);

            edit_btn = itemView.findViewById(R.id.waiter_order_items_updateBtn);
            del_btn = itemView.findViewById(R.id.waiter_order_items_delBtn);
        }
    }

}
