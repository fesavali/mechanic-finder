package com.felohscodes.onroadmech;

import android.content.Intent;
import android.os.Bundle;

import com.felohscodes.onroadmech.Adapter.JobRequestAdapter;
import com.felohscodes.onroadmech.Models.RequestModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class JobRequests extends AppCompatActivity {
    private RecyclerView recyclerView;
    private JobRequestAdapter requestAdapter;
    private List<RequestModel> mJobs;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_requests);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recycler_vieww);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mJobs = new ArrayList<>();

        readUsers();


    }

    private void readUsers() {
        intent = getIntent();
        final String companyName = intent.getStringExtra("companyName");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("serviceRequests");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mJobs.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    RequestModel model = snapshot.getValue(RequestModel.class);
                    mJobs.add(model);
                }
                requestAdapter = new JobRequestAdapter(getApplicationContext(), mJobs);
                recyclerView.setAdapter(requestAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}