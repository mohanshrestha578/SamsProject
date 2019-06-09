package com.example.samsproject;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.samsproject.Activities.AddCategory;
import com.example.samsproject.Activities.Admin.Item;
import com.example.samsproject.Models.Category;
import com.example.samsproject.Models.ModelItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Additem extends AppCompatActivity {

    private  static final int PICK_IMAGE_REQUEST=1;

    private EditText item_name;
    private Button choose_image;
    private Button image_selector;
    Spinner category_name;
    private EditText price;
    private ProgressBar mProgressBar;
    private EditText description;
    private EditText discount;
    private Spinner placement;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDataRef;

    private StorageTask mUploadTask;

    Category saveCatDetails;

    List<String> category_list = new ArrayList<>();

    ArrayAdapter<String> dataAdapter;

    ArrayAdapter<String> placementDataAdapter;

    ProgressDialog progress;

    List<String> placement_list = new ArrayList<>();

    String image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        item_name = findViewById(R.id.item_admin_create_name);
        choose_image = findViewById(R.id.item_admin_create_choose_image);
        image_selector = findViewById(R.id.item_admin_create_btn);
        price = findViewById(R.id.item_admin_create_price);
        description = findViewById(R.id.item_admin_create_description);
        discount = findViewById(R.id.item_admin_create_discount);
        placement = findViewById(R.id.item_admin_create_placement);

        placement_list.add("Normal");
        placement_list.add("Offer");
        placement_list.add("Our Special");

        mProgressBar = findViewById(R.id.item_admin_create_progress_bar);

        category_name = findViewById(R.id.item_admin_create_category_name);

        mStorageRef = FirebaseStorage.getInstance().getReference("imageUploads");
        mDataRef = FirebaseDatabase.getInstance().getReference("Items");

        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                image_uri = uri.toString();
            }
        });

        // initialize Adapter and set value
        dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, category_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_name.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();

        // initialize Adapter and set value
        placementDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, placement_list);
        placementDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placement.setAdapter(placementDataAdapter);
        placementDataAdapter.notifyDataSetChanged();

        progress = new ProgressDialog(this);

        addItemsOnCategory();

        choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

    }

    private void addItemsOnCategory() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("categories");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                category_list.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Category category = postSnapshot.getValue(Category.class);
                    category_list.add(category.getCategory_name());
                }
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    public void uploadItem(View view) {

        dataValidation();

        if(mUploadTask != null && mUploadTask.isInProgress()){
            Toast.makeText(this, "Upload in Progress", Toast.LENGTH_SHORT).show();
        }else {

            startLoader(progress);

            setUpModel();

            if (mImageUri != null) {
                StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));

                mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Handler handler = new Handler();

                        String uploadId = mDataRef.push().getKey();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mProgressBar.setProgress(0);
                            }
                        }, 500);
                        Toast.makeText(Additem.this, "Upload Successful", Toast.LENGTH_SHORT).show();



                        ModelItem uploadImage = new ModelItem(uploadId, item_name.getText().toString().trim(),
                                price.getText().toString().trim(),
                                image_uri,
                                description.getText().toString().trim(),
                                saveCatDetails.getId(),
                                saveCatDetails.getCategory_name(),
                                Integer.parseInt(discount.getText().toString()),
                                String.valueOf(placement.getSelectedItem()));

                        mDataRef.child(uploadId).setValue(uploadImage);

                        endLoader(progress);
                        emptyAllFields();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        endLoader(progress);
                        Toast.makeText(Additem.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        mProgressBar.setProgress((int) progress);
                    }
                });
            } else {
                Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setUpModel() {
        DatabaseReference catRef = FirebaseDatabase.getInstance().getReference("categories");
        Query cat_id = catRef.orderByChild("category_name").equalTo(String.valueOf(category_name.getSelectedItem()));

        cat_id.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot task : dataSnapshot.getChildren()) {
                    Category category = task.getValue(Category.class);
                    saveCatDetails = new Category(category.getId(), category.getCategory_name());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void emptyAllFields() {
        item_name.setText("");
        price.setText("");
        description.setText("");
        discount.setText("");
    }

    private String dataValidation() {

        if(TextUtils.isEmpty(item_name.getText().toString())){
            Toast.makeText(this, "Please enter name!", Toast.LENGTH_LONG).show();
            return null;
        }else if(TextUtils.isEmpty(price.getText().toString())){
            Toast.makeText(this, "Please enter price!", Toast.LENGTH_LONG).show();
            return null;
        }else if(TextUtils.isEmpty(description.getText().toString())){
            Toast.makeText(this, "Please enter description!", Toast.LENGTH_LONG).show();
            return null;
        }else if(TextUtils.isEmpty(String.valueOf(category_name.getSelectedItem()))){
            Toast.makeText(this, "Please select a category name!", Toast.LENGTH_LONG).show();
            return null;
        }else if(TextUtils.isEmpty(discount.getText().toString())){
            Toast.makeText(this, "Please enter a discount!", Toast.LENGTH_LONG).show();
            return null;
        }else if(TextUtils.isEmpty(String.valueOf(placement.getSelectedItem()))){
            Toast.makeText(this, "Please select a placement!", Toast.LENGTH_LONG).show();
            return null;
        }
        return null;
    }

    private void openFileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");//view only image file
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            mImageUri = data.getData();
        }
    }

    public void startLoader(ProgressDialog progress){
        progress.setMessage("Uploading Data...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }

    public void endLoader(ProgressDialog progress){
        progress.dismiss();
    }

    public void cancleAddAdminItem(View view) {
        startActivity(new Intent(getApplicationContext(), Item.class));
    }
}
