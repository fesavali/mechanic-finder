package com.felohscodes.onroadmech;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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
    FirebaseAuth mAuth;
    DatabaseReference reference;
    TextView cname;
    TextView clicence;
    TextView cemail;
    TextView cphone;
    TextView cservice;
    TextView cservice1;
    TextView cservice2;
    TextView clocation;
    Button logout;
    Button jobs;
    private Context mContext;
ProgressBar loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cname = findViewById(R.id.tv_cname);
        clicence = findViewById(R.id.tv_licence);
        cemail = findViewById(R.id.tv_mail);
        cphone = findViewById(R.id.tv_phone);
        cservice = findViewById(R.id.tv_service);
        cservice1 = findViewById(R.id.tv_service1);
        cservice2 = findViewById(R.id.tv_service2);
        clocation = findViewById(R.id.tv_mail1);
        logout = findViewById(R.id.btn_logout);
        jobs = findViewById(R.id.btn_jobs);
        loader = findViewById(R.id.prog_prof);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(CompanyProfile.this, MainActivity.class);
                startActivity(intent);
            }
        });



          retreive();


    }

    private void retreive() {
        loader.setVisibility(View.VISIBLE);
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("ServiceProvider");

        final String email = mAuth.getCurrentUser().getEmail();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           for(DataSnapshot snapshot : dataSnapshot.getChildren()){
               final String name = snapshot.child("cName").getValue(String.class);
               String service = snapshot.child("cService").getValue(String.class);
               String service1 = snapshot.child("cService1").getValue(String.class);
               String service2 = snapshot.child("pService").getValue(String.class);
               String phone = snapshot.child("phone").getValue(String.class);
               String license = snapshot.child("license").getValue(String.class);
               String location = snapshot.child("location").getValue(String.class);


               cname.setText(name);
               clicence.setText(license);
               cemail.setText(email);
               cphone.setText(phone);
               cservice.setText(service2);
               cservice1.setText(service1);
               cservice2.setText(service);
               clocation.setText(location);
            loader.setVisibility(View.GONE);

               jobs.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent intent = new Intent(CompanyProfile.this, JobRequests.class);
                       intent.putExtra("companyName", name);
                       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       startActivity(intent);
                   }
               });

           }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

}