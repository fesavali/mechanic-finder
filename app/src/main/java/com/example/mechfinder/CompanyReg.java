package com.example.mechfinder;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class CompanyReg extends AppCompatActivity {

    EditText cName;
    EditText cPhone;
    EditText service;
    EditText service1;
    EditText service2;
    EditText service3;
    EditText service4;
    DatabaseReference database;
    ProgressBar loader;
    Button go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_reg);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        database = FirebaseDatabase.getInstance().getReference("Companies");

        cName = findViewById(R.id.cr_name1);
        cPhone = findViewById(R.id.cr_phone);
        service = findViewById(R.id.cr_service);
        service1 = findViewById(R.id.cr_service1);
        service2 = findViewById(R.id.cr_service2);
        service3 = findViewById(R.id.cr_service3);
        service4 = findViewById(R.id.cr_service4);

        go = findViewById(R.id.cr_btn);
        loader = findViewById(R.id.prog_bar);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDb();
            }
        });


    }

    private void saveToDb() {
        String cOname = cName.getText().toString().trim();
        String cPhonc = cPhone.getText().toString().trim();
        String cService = service.getText().toString().trim();
        String cService1 = service1.getText().toString().trim();
        String cService2 = service2.getText().toString().trim();
        String cService3 = service3.getText().toString().trim();
        String cService4 = service4.getText().toString().trim();
        if (cOname.isEmpty()) {
            cName.setError("Company name Is Required");
            cName.requestFocus();
            return;
        }
        if (cPhonc.isEmpty()) {
            cPhone.setError("Phone Number Is Required");
            cPhone.requestFocus();
            return;
        }
        if (cService.isEmpty()) {
            service.setError("Atleast one service Is Required");
            service.requestFocus();
            return;
        }


        String uid = database.push().getKey();

        companyGetter getter = new companyGetter(uid, cPhonc,cOname, cService, cService1, cService2, cService3, cService4);

        database.child(uid).setValue(getter);
        loader.setVisibility(View.VISIBLE);
        database.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loader.setVisibility(View.GONE);
                Toast.makeText(CompanyReg.this, "Information Saved Successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CompanyReg.this, CompanyProfile.class);
                startActivity(intent);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loader.setVisibility(View.GONE);
                Toast.makeText(CompanyReg.this, "Registration Failed",Toast.LENGTH_LONG).show();
            }
        });



    }

    @Override
    public void onBackPressed() {

    }

}