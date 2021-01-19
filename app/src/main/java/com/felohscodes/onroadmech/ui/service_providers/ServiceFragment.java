package com.felohscodes.onroadmech.ui.service_providers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.felohscodes.onroadmech.R;
import com.felohscodes.onroadmech.ServiceLogin;
import com.felohscodes.onroadmech.ServiceRegistration;

public class ServiceFragment extends Fragment {

    private ServiceViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(ServiceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_service, container, false);
//        final TextView textView = root.findViewById(R.id.text_slideshow);
        final TextView nem = root.findViewById(R.id.textView4);
        final TextView trm = root.findViewById(R.id.terms);
        final TextView nem1 = root.findViewById(R.id.textView6);
        final TextView nem2 = root.findViewById(R.id.textView7);
        final TextView nem3 = root.findViewById(R.id.textView8);
        final TextView nem4 = root.findViewById(R.id.textView9);
        final Button btn = root.findViewById(R.id.reg_btn);
        final TextView nem5 = root.findViewById(R.id.textView10);
        final Button btn1 = root.findViewById(R.id.log_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "to reg page",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), ServiceRegistration.class);
                startActivity(intent);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "to log page",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), ServiceLogin.class);
                startActivity(intent);
            }
        });


//        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}
