package com.example.samsproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

public class CreateReservation extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EditText reservationName;
    private TextView reservationTime;
    private TextView generatedDate;
    private EditText tableNumber;
    private EditText contactNo;
    private Button generateTime;
    private Button generateDate;
    private Button bookTable;

    private DatabaseReference dbref;

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
        String reservedName = reservationName.getText().toString();
        String reservedTime = reservationTime.getText().toString();
        String reservedDate = generatedDate.getText().toString();
        String reservedTableNo = tableNumber.getText().toString();
        String contact = contactNo.getText().toString();

        if(TextUtils.isEmpty(reservedName)) {
            Toast.makeText(this, "Please, Enter the name of the reservation", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(reservedTime)){
            Toast.makeText(this, "Please,Enter the time of the reservation", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(reservedDate)){
            Toast.makeText(this, "Please, Enter the date of the reservation", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(contact)){
            Toast.makeText(this, "Please, Enter the contact number", Toast.LENGTH_SHORT).show();
        }else{
            String reservationId = dbref.push().getKey();
            Reservation reserve = new Reservation(reservedName, reservedTime, reservedDate, reservedTableNo, contact);
            dbref.child(reservationId).setValue(reserve);
            Toast.makeText(this, "You have successfully reserve a table in our restaurant", Toast.LENGTH_SHORT).show();
            Table tb = new Table();
            tb.setBookStatus(1);
            reservationName.setText("");
            reservationTime.setText("");
            generatedDate.setText("Date");
            tableNumber.setText("");
            contactNo.setText("");
        }
    }
}
