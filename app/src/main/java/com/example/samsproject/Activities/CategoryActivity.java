package com.example.samsproject.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.samsproject.Models.Category;
import com.example.samsproject.R;
import com.example.samsproject.RecyclerViews.RecyclerViewCategory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private static final String TAG = "CategoryActivity";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;

    private List<Category> categories;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        categories = new ArrayList<>();

        progress = new ProgressDialog(this);

        myRef = database.getReference("categories");

        getAllCategories();

        getSupportActionBar().setTitle("Categories List");
    }

    public void getAllCategories(){

        startLoader(progress);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                categories.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Category category = postSnapshot.getValue(Category.class);
                    categories.add(category);
                }

                initRecyclerViewCategory();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                endLoader(progress);
            }
        });

    }

    private void initRecyclerViewCategory(){
        Log.d(TAG, "initRecyclerViewCategory: from CategoryActivity");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerView = findViewById(R.id.show_rv_categories);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewCategory viewItem = new RecyclerViewCategory(this, categories);
        recyclerView.setAdapter(viewItem);
        endLoader(progress);
    }

    public void startLoader(ProgressDialog progress){
        progress.setMessage("Loading Data...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }

    public void endLoader(ProgressDialog progress){
        progress.dismiss();
    }

    public void createCategoryPage(View view) {
        startActivity(new Intent(getApplicationContext(), AddCategory.class));
    }
}
