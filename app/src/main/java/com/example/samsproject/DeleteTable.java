package com.example.samsproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteTable extends AppCompatActivity {

    private EditText tableNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_table);

        tableNumber = findViewById(R.id.tableNo);
    }

    public void addTable(View view) {
        startActivity(new Intent(getApplicationContext(), CreateTable.class));
    }

    public void deleteTable(View view) {
        String deleteTable = tableNumber.getText().toString();
//        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Table Information").child(deleteTable);
//        dbref.removeValue();
        deleteTable(deleteTable);
    }

    public void deleteTable(String tableNo){
//        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Table Information");
//        dbref.child(tableNo).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Toast.makeText(DeleteTable.this, "Table has been successfully deleted", Toast.LENGTH_SHORT).show();
//                finish();
//                return;
//            }
//        });
        DatabaseReference dbref;
        dbref = FirebaseDatabase.getInstance().getReference("Table Information").child(tableNo);
        dbref.setValue(null);

        Toast.makeText(this, "Table has been successfully deleted.", Toast.LENGTH_SHORT).show();
        finish();
        return;
    }
}
