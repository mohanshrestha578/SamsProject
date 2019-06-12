package com.example.samsproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.samsproject.Activities.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    public static String TAG = "Login";

    private CardView register_btn;
    private FirebaseAuth mAuth;
    private EditText username;
    private EditText password;

    String user_id = "";
    String user_role = "customer";
    String user_name = "";

    private static int SPLASH_TIME_OUT = 5000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
//        startActivity(new Intent(getApplicationContext(), Login.class));

        register_btn = (CardView) findViewById(R.id.register_btn_cv);
        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser != null){
            goToDashboard(currentUser);
        }
    }

    private void goToDashboard(FirebaseUser currentUser) {
        if(user_role == null){
            user_id = currentUser.getUid();
            user_name = currentUser.getEmail();
            user_role = "customer";
        }

        SharedPreferences.Editor editor = getSharedPreferences("user_info", MODE_PRIVATE).edit();

        editor.putString("uuid", currentUser.getUid());
        editor.putString("role", user_role);
        editor.putString("username", user_name);

        editor.apply();

        if(user_role.equals("customer")){
            startActivity(new Intent(this, CustomerNavigationDrawerActivity.class));
        }else if(user_role.equals("Chef")){
            startActivity(new Intent(this, ChefDrawer.class));
        }else if(user_role.equals("Waiter")){
            startActivity(new Intent(this, WaiterDrawer.class));
        }else if(user_role.equals("Admin")){
            startActivity(new Intent(this, AdminDrawer.class));
        }
    }


    public void login(View view) {
        final ProgressDialog progress = new ProgressDialog(this);
        startLoader(progress);

        String email = username.getText().toString();
        String pass = password.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email address must not be empty", Toast.LENGTH_SHORT).show();
            endLoader(progress);
            return;
        }

        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Password must not be empty", Toast.LENGTH_SHORT).show();
            endLoader(progress);
            return;
        }

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            endLoader(progress);
                            final FirebaseUser fuser = mAuth.getCurrentUser();

                            Query query1 = FirebaseDatabase.getInstance().getReference("Staff Information").orderByChild("staffEmailAddress").equalTo(fuser.getEmail());
                            query1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                                        Staff user = postSnapshot.getValue(Staff.class);
                                        if(user.getStaffName() != null){
                                            user_id = fuser.getUid();
                                            user_name = user.getStaffName();
                                            user_role = user.getRole();
                                        }
                                    }
                                    goToDashboard(fuser);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            endLoader(progress);
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

    public void registerPage(View view) {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }

    public void startLoader(ProgressDialog progress){
        progress.setMessage("Logging in...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }

    public void endLoader(ProgressDialog progress){
        progress.dismiss();
    }
}
