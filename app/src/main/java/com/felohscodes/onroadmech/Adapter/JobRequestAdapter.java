package com.felohscodes.onroadmech.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.felohscodes.onroadmech.Models.RequestModel;
import com.felohscodes.onroadmech.R;

import java.util.List;

public class JobRequestAdapter extends RecyclerView.Adapter<JobRequestAdapter.ViewHolder> {

    private Context mContext;
    private List<RequestModel> mJobs;

    public JobRequestAdapter(Context mContext, List<RequestModel> mJobs){
        this.mJobs = mJobs;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent,false);
        return new JobRequestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final RequestModel job = mJobs.get(position);
        holder.psyName.setText("Company Requested: " + job.getGarage());
        holder.psyPhone.setText("User Number: " +job.getPhone());
        holder.psySpeciality.setText("Service Requested: "+job.getService());


    }

    @Override
    public int getItemCount() {
        return mJobs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView psyName;
        public TextView psyPhone;
        public TextView psySpeciality;

        public ViewHolder(View itemView){
            super(itemView);

            psyName = itemView.findViewById(R.id.username);
            psyPhone = itemView.findViewById(R.id.phone);
            psySpeciality = itemView.findViewById(R.id.spec);
        }
    }
}

