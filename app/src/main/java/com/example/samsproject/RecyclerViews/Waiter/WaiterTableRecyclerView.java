package com.example.samsproject.RecyclerViews.Waiter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.samsproject.Activities.Waiter.ItemsForOrderActivity;
import com.example.samsproject.R;
import com.example.samsproject.Table;

import java.util.ArrayList;
import java.util.List;

public class WaiterTableRecyclerView extends RecyclerView.Adapter<WaiterTableRecyclerView.ViewHolder>{

    private static final String TAG = "WaiterTableRecyclerView";
    private Context context;
    private List<Table> mTable;

    public WaiterTableRecyclerView(Context context, List<Table> mTable) {
        this.context = context;
        this.mTable = mTable;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.waiter_table_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: ");

        final Table table = mTable.get(i);

        viewHolder.sn.setText((i+1)+".");
        viewHolder.table_number.setText(table.getTableNumber());

        viewHolder.add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ItemsForOrderActivity.class);
                intent.putExtra("table_id", table.getTableId());
                intent.putExtra("table_number", table.getTableNumber());

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mTable.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView sn;
        TextView table_number;
        Button add_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sn = itemView.findViewById(R.id.waiter_table_section_sn);
            table_number = itemView.findViewById(R.id.waiter_table_section_tableNumber);

            add_btn = itemView.findViewById(R.id.waiter_table_section_addBtn);
        }
    }

}
