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

public class RecyclerViewOffers extends RecyclerView.Adapter<RecyclerViewOffers.ViewHolder> {

    private static final String TAG = "RecyclerViewOffers";

    // vars
    private ArrayList<ModelItem> mItems = new ArrayList<>();
    private Context mContext;

    public RecyclerViewOffers(ArrayList<ModelItem> mItems, Context mContext) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    @NonNull
    @Override
    public RecyclerViewOffers.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_homepage_offers, viewGroup,false);
        return new RecyclerViewOffers.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewOffers.ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: ");

        final Integer pos = i;

        Glide.with(mContext)
                .asBitmap()
                .load(mItems.get(i).getImageUrl())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(viewHolder.image);

        String price = "Rs." + mItems.get(i).getPrice();
        String dis = mItems.get(i).getDiscount() + " % Off";

        viewHolder.title.setText(mItems.get(i).getName());
        viewHolder.category.setText(mItems.get(i).getCategory_name());
        viewHolder.price.setText(price);
        viewHolder.discount.setText(dis);

        viewHolder.itemOfferLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ItemViewActivity.class);
                intent.putExtra("id", mItems.get(pos).getId());
                intent.putExtra("name", mItems.get(pos).getName());
                intent.putExtra("imageUrl", mItems.get(pos).getImageUrl());
                intent.putExtra("price", mItems.get(pos).getPrice());
                intent.putExtra("description", mItems.get(pos).getDescription());
                intent.putExtra("discount", mItems.get(pos).getDiscount());
                intent.putExtra("category_id", mItems.get(pos).getCategory_id());
                intent.putExtra("category_name", mItems.get(pos).getCategory_name());

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title;
        TextView category;
        TextView price;
        TextView discount;
        RelativeLayout itemOfferLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.offer_image);
            title = itemView.findViewById(R.id.offer_food_name);
            category = itemView.findViewById(R.id.offer_food_category);
            price = itemView.findViewById(R.id.offer_food_price);
            discount = itemView.findViewById(R.id.offer_discount);
            itemOfferLayout = itemView.findViewById(R.id.itemOfferLayout);
        }
    }

}
