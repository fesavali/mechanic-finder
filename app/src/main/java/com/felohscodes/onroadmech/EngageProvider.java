package com.felohscodes.onroadmech;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.felohscodes.onroadmech.Models.RequestModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EngageProvider extends AppCompatActivity {
Spinner providers;
EditText wanteds;
EditText unumber;
Button sendR;
Button logout;
TextView userE;
FirebaseAuth auth;
DatabaseReference reference;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engage_provider);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        providers = findViewById(R.id.spinner_providers);
        wanteds = findViewById(R.id.edt_servicer);
        unumber = findViewById(R.id.edt_phoner);
        sendR = findViewById(R.id.bt_send_request);
        logout = findViewById(R.id.bt_logout_s);
        userE = findViewById(R.id.tv_s_mail);
        progressBar = findViewById(R.id.bar_request);

        auth = FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance().getReference("serviceRequests");

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(EngageProvider.this, MainActivity.class);
                startActivity(intent);
            }
        });

        userE.setText(auth.getCurrentUser().getEmail());

        sendR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });

    }

    private void sendRequest() {
        String garage = providers.getSelectedItem().toString().trim();
        String service = wanteds.getText().toString().trim();
        String phone = unumber.getText().toString().trim();

        RequestModel model = new RequestModel(garage, service, phone);

        progressBar.setVisibility(View.VISIBLE);
        reference.child(garage).setValue(model);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(EngageProvider.this, "Request send successfully", Toast.LENGTH_SHORT).show();
                sendR.setVisibility(View.GONE);
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
                Toast.makeText(EngageProvider.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}