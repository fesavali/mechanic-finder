package com.felohscodes.onroadmech;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();


        Button sign = findViewById(R.id.btn_sgn);
        TextView toLogin = findViewById(R.id.txt_to_login);


        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
//                Toast.makeText(SignUp.this, "Launch Login Activity",Toast.LENGTH_SHORT).show();
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        EditText mail = findViewById(R.id.edt_email);
        EditText pass = findViewById(R.id.edt_password);
        EditText pass2 = findViewById(R.id.edt_confirm);

        final ProgressBar loader = findViewById(R.id.re_progress3);

        final String email = mail.getText().toString().trim();
        final String password = pass.getText().toString().trim();
        String password2 = pass2.getText().toString().trim();

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
        if (password2.isEmpty()) {
            pass2.setError("Please Confirm Your Password");
            pass2.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mail.setError("Enter a Valid Email");
            mail.requestFocus();
            return;
        }
        if (password.length() < 6) {
            pass.setError("Password should be min 6 characters");
            pass.requestFocus();
            return;
        }
        if (!password.matches(password2)) {
            pass2.setError("Passwords do not match");
            pass2.requestFocus();
            return;
        }

        loader.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                loader.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(SignUp.this, "User Registerd Successfully",Toast.LENGTH_SHORT).show();
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUp.this, "You Are Logged In",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUp.this, EngageProvider.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUp.this, Login.class);
                                startActivity(intent);
                            }

                        }
                    });

                }else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
