package com.example.samsproject.RecyclerViews.Admin;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.samsproject.Models.Category;
import com.example.samsproject.Models.ModelItem;
import com.example.samsproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewAdminItem extends RecyclerView.Adapter<RecyclerViewAdminItem.ViewHolder> {

    private static final String TAG = "RecyclerViewAdminItem";
    private Context context;
    private List<ModelItem> mItem;
    TextView categories;
    List<String> category_list = new ArrayList<>();

    public RecyclerViewAdminItem(Context context, List<ModelItem> mItem) {
        this.context = context;
        this.mItem = mItem;
    }

    @NonNull
    @Override
    public RecyclerViewAdminItem.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_items, viewGroup, false);
        return new RecyclerViewAdminItem.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdminItem.ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: ");

        final ModelItem item = mItem.get(i);

        viewHolder.item_name.setText(item.getName());

        viewHolder.sn.setText((i+1) + ".");

        viewHolder.del_btn.setOnClickListener(new View.OnClickListener() {

            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Items").child(item.getId());

            @Override
            public void onClick(View v) {

                dbRef.removeValue();
                Toast.makeText(context,"Category Deleted Successfully!", Toast.LENGTH_LONG).show();
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
            sn = itemView.findViewById(R.id.list_admin_item_sn);
            item_name = itemView.findViewById(R.id.list_admin_item_name);

            edit_btn = itemView.findViewById(R.id.list_admin_item_editBtn);
            del_btn = itemView.findViewById(R.id.list_admin_item_delBtn);
        }
    }

    private void showUpdateDialog(final String categoryId, String imageUrl, String item_name, String price, String description, String category_name, Integer discount, String placement) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.layout_update_admin_items, null);
        dialogBuilder.setView(dialogView);

        final ImageView editImageUrl = dialogView.findViewById(R.id.item_admin_update_choose_image);

        Glide.with(context)
                .load(imageUrl)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(editImageUrl);

        final TextView editTextName = (TextView) dialogView.findViewById(R.id.item_admin_update_name);
        final TextView editPrice = (TextView) dialogView.findViewById(R.id.item_admin_update_price);
        final TextView editDescription = (TextView) dialogView.findViewById(R.id.item_admin_update_description);
        final TextView editDiscount = dialogView.findViewById(R.id.item_admin_update_discount);
        final TextView editPlacement = dialogView.findViewById(R.id.item_admin_update_placement);


        categories = (TextView) dialogView.findViewById(R.id.item_admin_update_category_name);

        categories.setText(category_name);
        editTextName.setText(item_name);
        editPrice.setText("Rs." + price);
        editDescription.setText(description);
        editDiscount.setText(discount + "%");
        editPlacement.setText(placement);

        addItemsOnCategory();

        final Button buttonCancle = (Button) dialogView.findViewById(R.id.item_admin_cancle_btn);

        dialogBuilder.setTitle("Food Item Details");
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                b.cancel();

            }
        });
    }

    private void addItemsOnCategory() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("categories");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                category_list.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Category category = postSnapshot.getValue(Category.class);
                    category_list.add(category.getCategory_name());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
