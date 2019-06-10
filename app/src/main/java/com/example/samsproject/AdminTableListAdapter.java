package com.example.samsproject;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdminTableListAdapter extends ArrayAdapter<Table> {

    private Activity context;
    private List<Table> tableList;

    public AdminTableListAdapter(Activity context, List<Table> tableList) {
        super(context, R.layout.activity_admin_table_lists, tableList);
        this.context = context;
        this.tableList = tableList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.table_item, null, true);

        TextView tNumber = listItemView.findViewById(R.id.tableNumber);
        TextView tSeats = listItemView.findViewById(R.id.numberOfSeats);

        final Table table = tableList.get(position);
        Intent intent = new Intent(context, AdminTableLists.class);
        intent.putExtra("id", table.getTableId());
        ImageButton deletebtn = listItemView.findViewById(R.id.deleteTable);
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Table Information");
                myRef.child(table.getTableId()).removeValue();
            }
        });


        tNumber.setText(table.getTableNumber());
        tSeats.setText(table.getNumberOfSeats());

        return listItemView;
    }
}
