package com.felohscodes.onroadmech;

import android.content.Intent;
import android.os.Bundle;

import com.felohscodes.onroadmech.Models.CompanyRegModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

public class ServiceRegistration extends AppCompatActivity {
    Spinner serviceOpt;
    EditText companyName;
    EditText cLicense;
    EditText cPhone;
    EditText cLocation;
    EditText cService;
    EditText cService1;
    EditText cEmail;
    EditText cPass;
    EditText cPass1;
    Button cReg;
    DatabaseReference reference;
    FirebaseAuth auth;
    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_reg_act);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        serviceOpt = findViewById(R.id.spin_services);
        companyName = findViewById(R.id.edt_name);
        cLicense = findViewById(R.id.edt_license);
        cPhone = findViewById(R.id.edt_phoner);
        cLocation = findViewById(R.id.edt_location);
        cService = findViewById(R.id.edt_servicer);
        cService1 = findViewById(R.id.edt_service1);
        cEmail = findViewById(R.id.edt_srv_mail);
        cPass = findViewById(R.id.edt_srv_pass);
        cPass1 = findViewById(R.id.edt_srv_pass1);
        cReg = findViewById(R.id.btn_reg_service);
        loader = findViewById(R.id.lod_bar);


        auth = FirebaseAuth.getInstance();

            cReg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    regCompany();
                }
            });

    }

    private void regCompany() {
        String pService = serviceOpt.getSelectedItem().toString().trim();
        String cName = companyName.getText().toString().trim();
        String license = cLicense.getText().toString().trim();
        String phone = cPhone.getText().toString().trim();
        String location = cLocation.getText().toString().trim();
        String service = cService.getText().toString().trim();
        String service1 = cService1.getText().toString().trim();
        final String email = cEmail.getText().toString().trim();
        final String password = cPass.getText().toString().trim();
        String password1 = cPass1.getText().toString().trim();

        if (cName.isEmpty()) {
            companyName.setError("Garage Name Is Required");
            companyName.requestFocus();
            return;
        }
        if (license.isEmpty()) {
            cLicense.setError("License Number Is Required");
            cLicense.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            cPhone.setError("Phone Number is Required");
            cPhone.requestFocus();
            return;
        }
        if (location.isEmpty()) {
            cLocation.setError("Indicate Garage Location");
            cLocation.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            cEmail.setError("Enter a Valid Email");
            cEmail.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            cEmail.setError("Email is Required");
            cEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            cPass.setError("Choose a Password");
            cPass.requestFocus();
            return;
        }
        if (password.length() < 6) {
            cPass.setError("Minimum 6 Character Password");
            cPass.requestFocus();
            return;
        }
        if (password1.isEmpty()) {
            cPass1.setError("Confirm your Password");
            cPass1.requestFocus();
            return;
        }
        if (!password.matches(password1)) {
            cPass1.setError("Passwords Don't Match");
            cPass1.requestFocus();
            return;
        }
        CompanyRegModel regModel = new CompanyRegModel(pService, cName, license, phone, location, service, service1);

        loader.setVisibility(View.VISIBLE);
        reference = FirebaseDatabase.getInstance().getReference("ServiceProvider");
        reference.child(cName).setValue(regModel);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Toast.makeText(ServiceRegistration.this, "Company Registered Successfully, Please wait as we set up your account",Toast.LENGTH_SHORT).show();
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ServiceRegistration.this, "User Created Successfully", Toast.LENGTH_SHORT).show();

                            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    loader.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ServiceRegistration.this, "You are now Logged In", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ServiceRegistration.this, CompanyProfile.class);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(getApplicationContext(), databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}