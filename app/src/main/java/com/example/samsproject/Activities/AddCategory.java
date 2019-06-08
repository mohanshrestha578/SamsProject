package com.example.samsproject.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.samsproject.Models.Category;
import com.example.samsproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddCategory extends AppCompatActivity {

    List Categories;
    EditText categoryName;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private String TAG = "AddCategoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        myRef = database.getReference("categories");

        categoryName = findViewById(R.id.add_category_name);

        Categories = new ArrayList();
    }

    public void createCategory(View view) {
        String category_name = categoryName.getText().toString().trim();

        if(!TextUtils.isEmpty(category_name)){
            String id = generateId();
            Category category = new Category(id, category_name);

            createCategory(category, id);

            categoryName.setText("");
            Toast.makeText(this, "New Category Created", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Please enter a category name.", Toast.LENGTH_SHORT).show();
        }

    }

    public String generateId(){
        return myRef.push().getKey();
    }

    public void createCategory(Category category, String id){
        myRef.child(id).setValue(category);
    }

    public void cancelCategory(View view){
        startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
    }
}
