package com.example.samsproject;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomerTableListAdapter extends ArrayAdapter<Table> {

    private Activity context;
    private List<Table> tableLists;

    public CustomerTableListAdapter(Activity context, List<Table> tableList) {
        super(context, R.layout.activity_admin_table_lists, tableList);
        this.context = context;
        this.tableLists = tableList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.customer_table_item, null, true);

        TextView tNumber = listItemView.findViewById(R.id.tableNumber);
        TextView tSeats = listItemView.findViewById(R.id.numberOfSeats);

        Table table = tableLists.get(position);
        tNumber.setText(table.getTableNumber());
        tSeats.setText(table.getNumberOfSeats());

        return listItemView;
    }
}

