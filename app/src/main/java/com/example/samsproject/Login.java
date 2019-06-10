package com.example.samsproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.samsproject.Activities.CategoryActivity;
import com.example.samsproject.Activities.HomepageActivity;
import com.example.samsproject.Activities.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    public static String TAG = "Login";

    private CardView register_btn;
    private FirebaseAuth mAuth;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

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
            startActivity(new Intent(this, HomepageActivity.class));
        }
    }

    public void forgotPassword(View view) {
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
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            endLoader(progress);
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
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
