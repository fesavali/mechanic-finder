package com.example.mechfinder;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class ServiceProviderReg extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText mail;
    EditText pass;
    EditText pass1;
    Button reg;
    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_reg);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mail = findViewById(R.id.spr_mail);
        pass = findViewById(R.id.spr_ps);
        pass1 = findViewById(R.id.spr_ps1);
        reg = findViewById(R.id.spr_btn);
        loader = findViewById(R.id.bar);

        mAuth = FirebaseAuth.getInstance();

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegService();
            }
        });
    }

    private void RegService() {
        String email = mail.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String password1 = pass1.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mail.setError("Enter a valid Email Address");
            mail.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            mail.setError("Email is Required");
            mail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            pass.setError("Password is Required");
            pass.requestFocus();
            return;
        }
        if (password.length() < 6) {
            pass.setError("Minimum password Length is 6 Characters");
            pass.requestFocus();
            return;
        }
        if (password1.isEmpty()) {
            pass1.setError("Please Confirm Password");
            pass1.requestFocus();
            return;
        }
        if (!password.matches(password1)) {
            pass1.setError("The Passwords don't Match");
            pass1.requestFocus();
            return;
        }
        loader.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                loader.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(ServiceProviderReg.this, "User Registered successfully", Toast.LENGTH_SHORT).show();
                    mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ServiceProviderReg.this, "You are Now Logged In", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ServiceProviderReg.this, CompanyReg.class);
                                startActivity(intent);
                            }

                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}