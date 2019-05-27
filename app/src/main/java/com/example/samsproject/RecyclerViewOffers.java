package com.example.samsproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewOffers extends RecyclerView.Adapter<RecyclerViewOffers.ViewHolder> {

    private static final String TAG = "RecyclerViewOffers";

    // vars
    private ArrayList<String> mTitle = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mPrice = new ArrayList<>();
    private ArrayList<String> mDiscount = new ArrayList<>();
    private ArrayList<String> mCategory = new ArrayList<>();
    private Context mContext;

    public RecyclerViewOffers(ArrayList<String> mTitle, ArrayList<String> mImageUrls, ArrayList<String> mPrice, ArrayList<String> mDiscount, ArrayList<String> mCategory, Context mContext) {
        this.mTitle = mTitle;
        this.mImageUrls = mImageUrls;
        this.mPrice = mPrice;
        this.mDiscount = mDiscount;
        this.mCategory = mCategory;
        this.mContext = mContext;
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
        Glide.with(mContext)
                .asBitmap()
                .load(mImageUrls.get(i))
                .into(viewHolder.image);

        viewHolder.title.setText(mTitle.get(i));
        viewHolder.category.setText(mCategory.get(i));
        viewHolder.price.setText(mPrice.get(i));
        viewHolder.discount.setText(mDiscount.get(i));
    }

    @Override
    public int getItemCount() {
        return mTitle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title;
        TextView category;
        TextView price;
        TextView discount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.offer_image);
            title = itemView.findViewById(R.id.offer_food_name);
            category = itemView.findViewById(R.id.offer_food_category);
            price = itemView.findViewById(R.id.offer_food_price);
            discount = itemView.findViewById(R.id.offer_discount);
        }
    }

}
