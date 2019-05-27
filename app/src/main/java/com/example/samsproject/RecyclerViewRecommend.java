package com.example.samsproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewRecommend extends RecyclerView.Adapter<RecyclerViewRecommend.ViewHolder> {

    private static final String TAG = "RecyclerViewOffers";

    // vars
    private Context mContext;
    private ArrayList<String> mTitle = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mPrice = new ArrayList<>();

    public RecyclerViewRecommend(Context mContext, ArrayList<String> mTitle, ArrayList<String> mImageUrls, ArrayList<String> mPrice) {
        this.mContext = mContext;
        this.mTitle = mTitle;
        this.mImageUrls = mImageUrls;
        this.mPrice = mPrice;
    }

    @NonNull
    @Override
    public RecyclerViewRecommend.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewRecommend.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title;
        TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.recommend_dish_image);
            title = itemView.findViewById(R.id.recommend_dish_name);
            price = itemView.findViewById(R.id.recommend_dish_price);
        }
    }

}
