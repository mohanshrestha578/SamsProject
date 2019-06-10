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
import com.example.samsproject.Models.Role;
import com.example.samsproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RecyclerViewRole extends RecyclerView.Adapter<RecyclerViewRole.ViewHolder>{

    private static final String TAG = "RecyclerViewRole";
    private Context context;
    private List<Role> mRole;

    public RecyclerViewRole(Context context, List<Role> mRole) {
        this.context = context;
        this.mRole = mRole;
    }

    @NonNull
    @Override
    public RecyclerViewRole.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_roles, viewGroup, false);
        return new RecyclerViewRole.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: ");
        final Role role = mRole.get(i);
        viewHolder.role_name.setText(role.getRole_name());

        viewHolder.sn.setText((i+1) + ".");

        viewHolder.del_btn.setOnClickListener(new View.OnClickListener() {

            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("roles").child(role.getId());

            @Override
            public void onClick(View v) {

                dbRef.removeValue();
                Toast.makeText(context,"Role Deleted Successfully!", Toast.LENGTH_LONG).show();
            }
        });

        viewHolder.edit_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showUpdateDialog(role.getId(), role.getRole_name());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRole.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView sn;
        TextView role_name;
        Button edit_btn;
        Button del_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sn = itemView.findViewById(R.id.table_role_sn);
            role_name = itemView.findViewById(R.id.table_role_name);
            edit_btn = itemView.findViewById(R.id.table_role_edit_btn);
            del_btn = itemView.findViewById(R.id.table_role_delete_btn);
        }
    }

    private void showUpdateDialog(final String roleId, String roleName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.layout_update_role, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.update_role_name);
        editTextName.setText(roleName);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.updateRole_btn);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.updateRole_cancleBtn);

        dialogBuilder.setTitle("Update Role Name");
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    updateRole(roleId, name);
                    b.dismiss();
                }
            }
        });


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                b.cancel();

            }
        });
    }

    private void updateRole(String id, String name){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("roles").child(id);

        Role role = new Role(id, name);
        dbRef.setValue(role);

        Toast.makeText(context, "Role Updated", Toast.LENGTH_LONG).show();

    }

}
