package com.example.mechfinder;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class UserLogin extends AppCompatActivity {

    public static final int GOOGLE_SIGN_IN_CODE = 10005;
    private FirebaseAuth mAuth;
    EditText mail;
    EditText pass;
    Button login;
    Button signG;
    TextView toReg;
    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mail = findViewById(R.id.edt_mail);
        pass = findViewById(R.id.ed_passw);
        login = findViewById(R.id.btn_login);
        signG = findViewById(R.id.btn_sign);
        toReg = findViewById(R.id.tv_reg);
        loader = findViewById(R.id.bar2);

        mAuth = FirebaseAuth.getInstance();

//        mAuth.getCurrentUser().getDisplayName();
//        Log.d("tag","Oncreate: "+ mAuth.getCurrentUser().getEmail() +mAuth.getCurrentUser().getDisplayName());

        toReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLogin.this, NewUser.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUser();
            }
        });
        signG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions gso;
                GoogleSignInClient signInClient;

                gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken("69904232639-fkqp6544stodem7ou9jcv7tg6bje4o3f.apps.googleusercontent.com")
                        .requestEmail()
                        .build();
                signInClient = GoogleSignIn.getClient(UserLogin.this, gso);

                GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(UserLogin.this);
                if(signInAccount != null || mAuth.getCurrentUser() != null){
                    Toast.makeText(UserLogin.this, "User Logged In Already", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserLogin.this, GetService.class);
                    startActivity(intent);
                }else{
                    Intent sign = signInClient.getSignInIntent();
                    startActivityForResult(sign, GOOGLE_SIGN_IN_CODE);
                }

            }
        });



    }

    private void signUser() {
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
                    Toast.makeText(UserLogin.this, "Login Successful",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserLogin.this, GetService.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN_CODE) {
            Task<GoogleSignInAccount> signInTask = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount signInAcc = signInTask.getResult(ApiException.class);
                AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAcc.getIdToken(),null);

                mAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(UserLogin.this, "Successfully added, sign user in",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UserLogin.this, GetService.class);
                        startActivity(intent);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserLogin.this, "User Registration Failed",Toast.LENGTH_SHORT).show();
                    }
                });
                Toast.makeText(this, "Google Account Connected",Toast.LENGTH_SHORT).show();
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser firebaseUser = mAuth.getCurrentUser();
//        if(firebaseUser != null){
//            Toast.makeText(this, "You Are Already Logged in",Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this, GetService.class);
//            startActivity(intent);
//        }

}