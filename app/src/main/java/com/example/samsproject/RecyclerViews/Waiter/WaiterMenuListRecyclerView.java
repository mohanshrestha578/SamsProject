package com.example.samsproject.RecyclerViews.Waiter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.samsproject.Models.ModelItem;
import com.example.samsproject.Models.Order;
import com.example.samsproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class WaiterMenuListRecyclerView extends RecyclerView.Adapter<WaiterMenuListRecyclerView.ViewHolder> {

    private static final String TAG = "RecyclerViewAdminItem";
    private Context context;
    private List<ModelItem> mItem;
    private String user_id;
    private String user_name;
    private String table_number;

    public WaiterMenuListRecyclerView(Context context, List<ModelItem> mItem, String user_id, String user_name, String table_number) {
        this.context = context;
        this.mItem = mItem;
        this.user_id = user_id;
        this.user_name = user_name;
        this.table_number = table_number;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.waiter_menu_list_items, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: ");

        final ModelItem item = mItem.get(i);

        viewHolder.item_name.setText(item.getName());

        viewHolder.sn.setText((i+1) + ".");

        viewHolder.del_btn.setOnClickListener(new View.OnClickListener() {

            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Items").child(item.getId());

            @Override
            public void onClick(View v) {

                dbRef.removeValue();
                Toast.makeText(context,"Order Deleted Successfully!", Toast.LENGTH_LONG).show();
            }
        });

        viewHolder.edit_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showUpdateDialog(item.getId(), item.getImageUrl(), item.getName(), item.getPrice(), item.getDescription(), item.getCategory_name(), item.getDiscount(), item.getPlacement());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView sn;
        TextView item_name;

        Button edit_btn;
        Button del_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sn = itemView.findViewById(R.id.list_waiter_item_sn);
            item_name = itemView.findViewById(R.id.list_waiter_item_name);

            edit_btn = itemView.findViewById(R.id.list_waiter_item_editBtn);
            del_btn = itemView.findViewById(R.id.list_waiter_item_delBtn);
        }
    }

    private void showUpdateDialog(final String itemId, final String imageUrl, final String item_name, final String price, String description, String category_name, final Integer discount, String placement) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.dialog_add_menu_item, null);
        dialogBuilder.setView(dialogView);

        final TextView item_quantity = dialogView.findViewById(R.id.menu_item_quantity);
        final Button submitBtn = dialogView.findViewById(R.id.menu_item_quantity_submitBtn);

        dialogBuilder.setTitle("Order Food Item");
        final AlertDialog b = dialogBuilder.create();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Integer.parseInt(item_quantity.getText().toString()) > 0){
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Orders");
                    String id = dbRef.push().getKey();

                    Order order = new Order(id,user_id,user_name,itemId,item_name,Integer.parseInt(price),Integer.parseInt(item_quantity.getText().toString()),imageUrl,discount,"order placed",table_number, 1);
                    dbRef.child(id).setValue(order);
                    Toast.makeText(context, "Order Successfully Placed!", Toast.LENGTH_LONG).show();

                    b.cancel();

                }else{
                    Toast.makeText(context, "Quantity must be more than 0!", Toast.LENGTH_LONG).show();
                }


            }
        });


        b.show();


    }

}
