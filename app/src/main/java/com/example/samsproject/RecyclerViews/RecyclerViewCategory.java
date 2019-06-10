package com.example.samsproject.RecyclerViews;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsproject.Models.Category;
import com.example.samsproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RecyclerViewCategory extends RecyclerView.Adapter<RecyclerViewCategory.ViewHolder> {

    private static final String TAG = "RecyclerViewCategory";
    private Context context;
    private List<Category> mCategory;

    public RecyclerViewCategory(Context context, List<Category> mCategory) {
        this.context = context;
        this.mCategory = mCategory;
    }

    @NonNull
    @Override
    public RecyclerViewCategory.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_categories, viewGroup, false);
        return new RecyclerViewCategory.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: ");
        final Category category = mCategory.get(i);
        viewHolder.category_name.setText(category.getCategory_name());

        viewHolder.sn.setText((i+1) + ".");

        viewHolder.del_btn.setOnClickListener(new View.OnClickListener() {

            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("categories").child(category.getId());

            @Override
            public void onClick(View v) {

                dbRef.removeValue();
                Toast.makeText(context,"Category Deleted Successfully!", Toast.LENGTH_LONG).show();
            }
        });

        viewHolder.edit_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showUpdateDialog(category.getId(), category.getCategory_name());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView sn;
        TextView category_name;
        Button edit_btn;
        Button del_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sn = itemView.findViewById(R.id.table_category_sn);
            category_name = itemView.findViewById(R.id.table_category_name);
            edit_btn = itemView.findViewById(R.id.table_category_edit_btn);
            del_btn = itemView.findViewById(R.id.table_category_delete_btn);
        }
    }

    private void showUpdateDialog(final String categoryId, String categoryName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.layout_update_category, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.update_category_name);
        editTextName.setText(categoryName);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.updateCategory_btn);
        final Button buttonCancle = (Button) dialogView.findViewById(R.id.updateCategory_cancleBtn);

        dialogBuilder.setTitle("Update Category Name");
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    updateCategory(categoryId, name);
                    b.dismiss();
                }
            }
        });


        buttonCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                b.cancel();

            }
        });
    }

    private void updateCategory(String id, String name){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("categories").child(id);

        Category category = new Category(id, name);

        if(category.getCategory_name().equals(null)){
            Toast.makeText(context, "Category Name must not be empty", Toast.LENGTH_LONG).show();
        }else{
            dbRef.setValue(category);

            Toast.makeText(context, "Category Updated", Toast.LENGTH_LONG).show();
        }


    }

}
