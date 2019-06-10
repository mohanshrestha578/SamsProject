package com.example.samsproject;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.net.ContentHandler;
import java.util.List;

public class AdminStaffListAdapter extends ArrayAdapter<Staff> {
    private Activity context;
    private List<Staff> staffList;

    public AdminStaffListAdapter(Activity context, List<Staff> staffList) {
        super(context, R.layout.activity_admin_staff_lists, staffList);
        this.context = context;
        this.staffList = staffList;
    }

    @Nullable
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View staffListView = inflater.inflate(R.layout.staff_item, null, true);

        TextView nameOfStaff = staffListView.findViewById(R.id.staffName);

        Staff staff = staffList.get(position);
        nameOfStaff.setText(staff.getStaffName());

        return staffListView;
    }
}
