package com.example.samsproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.samsproject.Activities.HomepageActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText conf_password;
    private ImageButton registerBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.reg_username);
        password = (EditText) findViewById(R.id.reg_password);
        conf_password = (EditText) findViewById(R.id.reg_conf_password);
        registerBtn = (ImageButton) findViewById(R.id.btnRegister);

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

    }

    public void loginPage(View view) {
        startActivity(new Intent(getApplicationContext(), Login.class));
    }
    public void registerUser(View view) {
        String email = username.getText().toString();
        String auth_password = password.getText().toString();
        String auth_conf_password = conf_password.getText().toString();

        if(!isValidEmail(email)){
            Toast.makeText(getApplicationContext(), "Email must be valid!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please provide email address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(auth_password)) {
            Toast.makeText(getApplicationContext(), "Please provide password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (auth_password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Your password must have more than 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(auth_conf_password)) {
            Toast.makeText(getApplicationContext(), "Please provide confirmation password", Toast.LENGTH_SHORT).show();
        }
        if (auth_password.equals(auth_conf_password)) {

            mAuth.createUserWithEmailAndPassword(email, auth_password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"E-mail or password is wrong",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        } else {
            Toast.makeText(getApplicationContext(), "Your password and confirmation does not match.", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}
