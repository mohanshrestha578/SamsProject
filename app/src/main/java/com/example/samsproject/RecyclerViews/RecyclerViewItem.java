package com.example.samsproject.RecyclerViews;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.samsproject.ItemViewActivity;
import com.example.samsproject.Models.ModelItem;
import com.example.samsproject.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewItem extends RecyclerView.Adapter<RecyclerViewItem.ViewHolder> {

    private static final String TAG = "RecyclerViewAdminItem";

    // vars
    private Context mContext;

    private List<ModelItem> itemDetails = new ArrayList<>();

    public RecyclerViewItem(Context mContext, ArrayList<ModelItem> itemDetails) {
        this.itemDetails = itemDetails;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listview_homepage_sm, viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: ");

        final Integer pos = i;

        Glide.with(mContext)
                .asBitmap()
                .load(itemDetails.get(i).getImageUrl())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(viewHolder.image);

        viewHolder.title.setText(itemDetails.get(i).getName());
        viewHolder.price.setText("Rs." + itemDetails.get(i).getPrice());

        viewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ItemViewActivity.class);
                intent.putExtra("id", itemDetails.get(pos).getId());
                intent.putExtra("name", itemDetails.get(pos).getName());
                intent.putExtra("imageUrl", itemDetails.get(pos).getImageUrl());
                intent.putExtra("price", itemDetails.get(pos).getPrice());
                intent.putExtra("description", itemDetails.get(pos).getDescription());
                intent.putExtra("discount", itemDetails.get(pos).getDiscount());
                intent.putExtra("category_id", itemDetails.get(pos).getCategory_id());
                intent.putExtra("category_name", itemDetails.get(pos).getCategory_name());

                mContext.startActivity(intent);
            }
        });

//        viewHolder.image.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            }
//        );
    }

    @Override
    public int getItemCount() {
        return itemDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title;
        TextView price;

        RelativeLayout itemLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_image);
            title = itemView.findViewById(R.id.item_name);
            price = itemView.findViewById(R.id.item_price);

            itemLayout = itemView.findViewById(R.id.itemLayout);
        }
    }

}
