package com.example.mechfinder;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class CompanyProfile extends AppCompatActivity {
    TextView cName;
    TextView cPhone;
    TextView cService;
    TextView cService1;
    TextView cService2;
    TextView cService3;
    TextView cService4;
    Button jobs;
    Button logout;
    FirebaseAuth mAuth;
    DatabaseReference database;
    ProgressBar load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        load = findViewById(R.id.bar_info);

        mAuth = FirebaseAuth.getInstance();

        TextView view = (TextView) findViewById(R.id.tv_userd);
        view.setText( mAuth.getCurrentUser().getEmail());
        getInfor();
//        jobs  = findViewById(R.id.button3);
        logout  = findViewById(R.id.logout);
//        jobs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(CompanyProfile.this, ServiceProviders.class);
                startActivity(intent);
            }
        });


    }

    private void getInfor() {
        cName = findViewById(R.id.c_name);
        cPhone = findViewById(R.id.c_phone);
        cService = findViewById(R.id.c_service1);
        cService1 = findViewById(R.id.c_service2);
        cService2 = findViewById(R.id.c_service3);
        cService3 = findViewById(R.id.c_serice4);
        cService4 = findViewById(R.id.c_service);

        database = FirebaseDatabase.getInstance().getReference("Companies");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    String uid = ds.child("uid").getValue(String.class);
                    String CName = ds.child("company").getValue(String.class);
                    String phone = ds.child("phone").getValue(String.class);
                    String Service = ds.child("servic").getValue(String.class);
                    String Service1 = ds.child("servic1").getValue(String.class);
                    String service2 = ds.child("servic2").getValue(String.class);
                    String service3 = ds.child("servic3").getValue(String.class);
                    String service4 = ds.child("servic4").getValue(String.class);


                    cName.setText(CName);
                    cPhone.setText(phone);
                    cService.setText(Service);
                    cService1.setText(Service1);
                    cService2.setText(service2);
                    cService3.setText(service3);
                    cService4.setText(service4);
                }


                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CompanyProfile.this, "Data Fetch Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}