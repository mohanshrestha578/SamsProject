package com.example.samsproject.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.samsproject.Login;
import com.example.samsproject.Models.ModelItem;
import com.example.samsproject.Models.User;
import com.example.samsproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Set;

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText username;
    private EditText password;
    private EditText conf_password;
    private ImageButton registerBtn;
    private FirebaseAuth mAuth;

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.reg_email);
        username = findViewById(R.id.reg_username);
        password = findViewById(R.id.reg_password);
        conf_password =  findViewById(R.id.reg_conf_password);
        registerBtn = findViewById(R.id.btnRegister);

        progress = new ProgressDialog(this);

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

        Query dbUser = FirebaseDatabase.getInstance()
                .getReference("users").orderByChild("uuid").equalTo(mAuth.getUid());
        dbUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    User user = postSnapshot.getValue(User.class);

                    SharedPreferences.Editor editor = getSharedPreferences("user_info", MODE_PRIVATE).edit();
                    editor.putString("id", user.getId());
                    editor.putString("uuid", mAuth.getUid());
                    editor.putString("username", user.getName());
                    editor.apply();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void loginPage(View view) {
        startActivity(new Intent(getApplicationContext(), Login.class));
    }
    public void registerUser(View view) {


        final String auth_email = email.getText().toString();
        final String auth_username = username.getText().toString();
        String auth_password = password.getText().toString();
        String auth_conf_password = conf_password.getText().toString();

        if (TextUtils.isEmpty(auth_email)) {
            Toast.makeText(getApplicationContext(), "Please provide email address", Toast.LENGTH_LONG).show();
            return;
        }

        if(!isValidEmail(auth_email)){
            Toast.makeText(getApplicationContext(), "Email must be valid!", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(auth_password)) {
            Toast.makeText(getApplicationContext(), "Please provide password", Toast.LENGTH_LONG).show();
            return;
        }
        if (auth_password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Your password must have more than 6 characters", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(auth_conf_password)) {
            Toast.makeText(getApplicationContext(), "Please provide confirmation password", Toast.LENGTH_LONG).show();
        }
        if (auth_password.equals(auth_conf_password)) {
            endLoader(progress);
            mAuth.createUserWithEmailAndPassword(auth_email, auth_password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                DatabaseReference db = FirebaseDatabase.getInstance().getReference("users");

                                String id = db.push().getKey();

                                User user = new User(id, mAuth.getUid(), auth_username,auth_email);

                                db.child(id).setValue(user);

                                SharedPreferences.Editor editor = getSharedPreferences("user_info", MODE_PRIVATE).edit();
                                editor.putString("id", id);
                                editor.putString("uuid", mAuth.getUid());
                                editor.putString("username", auth_username);
                                editor.putString("role", "customer");
                                editor.apply();

                                endLoader(progress);

                                startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"E-mail or password is wrong",Toast.LENGTH_LONG).show();
                                endLoader(progress);
                            }
                        }
                    });

        } else {
            Toast.makeText(getApplicationContext(), "Your password and confirmation does not match.", Toast.LENGTH_LONG).show();
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
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
