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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.samsproject.ItemViewActivity;
import com.example.samsproject.Models.ModelItem;
import com.example.samsproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewRecommend extends RecyclerView.Adapter<RecyclerViewRecommend.ViewHolder> {

    private static final String TAG = "RecyclerViewRecommend";

    // vars
    private Context mContext;
    private ArrayList<ModelItem> mItems = new ArrayList<>();

    public RecyclerViewRecommend(Context mContext, ArrayList<ModelItem> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
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

        final Integer pos = i;

        Glide.with(mContext)
                .load(mItems.get(i).getImageUrl())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(viewHolder.image);

        String price = "Rs." + mItems.get(i).getPrice();

        viewHolder.title.setText(mItems.get(i).getName());
        viewHolder.price.setText(price);

        viewHolder.recommendLayout.setOnClickListener(new View.OnClickListener() {
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
        TextView price;
        RelativeLayout recommendLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.recommend_dish_image);
            title = itemView.findViewById(R.id.recommend_dish_name);
            price = itemView.findViewById(R.id.recommend_dish_price);
            recommendLayout = itemView.findViewById(R.id.recommendLayout);
        }
    }

}
