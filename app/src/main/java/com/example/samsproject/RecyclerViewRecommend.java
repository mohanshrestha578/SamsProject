package com.example.samsproject;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewRecommend extends RecyclerView.Adapter<RecyclerViewRecommend.ViewHolder> {

    private static final String TAG = "RecyclerViewRecommend";

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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recommended_dish, viewGroup, false);
        return new RecyclerViewRecommend.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: ");


        Picasso.get().load(mImageUrls.get(i)).into(viewHolder.image);

        viewHolder.title.setText(mTitle.get(i));
        viewHolder.price.setText(mPrice.get(i));
    }

    @Override
    public int getItemCount() {
        return mTitle.size();
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
