package com.example.samsproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class CreateReservation extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EditText reservationName;
    private TextView reservationTime;
    private TextView generatedDate;
    private TextView tableNumber;
    private EditText contactNo;
    private Button generateTime;
    private Button generateDate;
    private Button bookTable;

    private DatabaseReference dbref;
    private DatabaseReference dbtab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservation);

        reservationName = findViewById(R.id.reservationName);
        reservationTime = findViewById(R.id.reservationTime);
        generatedDate = findViewById(R.id.generatedDate);
        tableNumber = findViewById(R.id.tableNumber);
        contactNo = findViewById(R.id.contactNumber);
        generateTime = findViewById(R.id.generateTime);
        bookTable = findViewById(R.id.makeReservation);

        generateDate = findViewById(R.id.generateDate);

        //generate date
        generateDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        //generate time
        generateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        dbref = FirebaseDatabase.getInstance().getReference("Reservation Details");
        dbtab = FirebaseDatabase.getInstance().getReference("Table Information");

        Intent intent = getIntent();
        //sets the table number
        tableNumber.setText(intent.getStringExtra("tableNumber"));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView textView = findViewById(R.id.generatedDate);
        textView.setText(currentDateString);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        reservationTime.setText(hourOfDay+":"+minute);

    }

    public void makeReservation(View view) {
        Intent intent = getIntent();
        Table table = new Table();
        String tableId = intent.getStringExtra("tableId");
        String tableNumber = intent.getStringExtra("tableNumber");
        String tablestat = intent.getStringExtra("tableStatus");


        if(tablestat.equals("unbooked")) {
            String reservedName = reservationName.getText().toString();
            String reservedTime = reservationTime.getText().toString();
            String reservedDate = generatedDate.getText().toString();
            String reservedTableNo = tableNumber;
            String contact = contactNo.getText().toString();

            if (TextUtils.isEmpty(reservedName)) {
                Toast.makeText(this, "Please, Enter the name of the reservation", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(reservedTime)) {
                Toast.makeText(this, "Please,Enter the time of the reservation", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(reservedDate)) {
                Toast.makeText(this, "Please, Enter the date of the reservation", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(contact)) {
                Toast.makeText(this, "Please, Enter the contact number", Toast.LENGTH_SHORT).show();
            } else {
                String reservationId = dbref.push().getKey();

                SharedPreferences prefs = getSharedPreferences("user_info", MODE_PRIVATE);
                String uuid = prefs.getString("uuid", null);
                String user_name = prefs.getString("username", null);

                Reservation reserve = new Reservation(reservedName, reservedTime, reservedDate, reservedTableNo, contact, user_name, uuid);
                dbref.child(reservationId).setValue(reserve);
                Toast.makeText(this, "You have successfully reserve a table in our restaurant", Toast.LENGTH_SHORT).show();
                reservationName.setText("");
                reservationTime.setText("");
                generatedDate.setText("Date");
                contactNo.setText("");
                Table tb = new Table();
                HashMap map = new HashMap();
                map.put("bookStatus", "booked");
                dbtab.child(tableId).updateChildren(map);

            }
        }else{
            Toast.makeText(this, "Table is already booked", Toast.LENGTH_SHORT).show();
        }
    }
}
