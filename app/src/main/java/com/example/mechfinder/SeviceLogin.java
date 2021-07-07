package com.example.mechfinder;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SeviceLogin extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText mail;
    EditText pass;
    Button login;
    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sevice_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mail = findViewById(R.id.sl_mail);
        pass = findViewById(R.id.sl_pass);
        loader = findViewById(R.id.bar1);

        login = findViewById(R.id.sl_btn);

        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String email = mail.getText().toString().trim();
        String password = pass.getText().toString().trim();

        if (email.isEmpty()) {
            mail.setError("Email is Required");
            mail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            pass.setError("Password Is required");
            pass.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mail.setError("Enter a Valid Email");
            mail.requestFocus();
            return;
        }
        loader.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                loader.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(SeviceLogin.this, "User Logged in successfully",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SeviceLogin.this, CompanyProfile.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}