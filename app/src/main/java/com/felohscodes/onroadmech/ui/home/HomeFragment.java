package com.felohscodes.onroadmech.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.felohscodes.onroadmech.CarRepairHelp;
import com.felohscodes.onroadmech.Login;
import com.felohscodes.onroadmech.MechanicsMap;
import com.felohscodes.onroadmech.R;
import com.felohscodes.onroadmech.TowMap;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final ImageButton getMech = root.findViewById(R.id.btn_get_mech);
        final ImageButton Tow = root.findViewById(R.id.btn_tow);
        final ImageButton help = root.findViewById(R.id.btn_help);
        final ImageButton drivers = root.findViewById(R.id.btn_drivers);

        getMech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Launch mechanics Map", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MechanicsMap.class);
                startActivity(intent);
            }
        });
        Tow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Launch Towing Services activity",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), TowMap.class);
                startActivity(intent);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CarRepairHelp.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "Launching Help Site",Toast.LENGTH_SHORT).show();
            }
        });
        drivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Launch drivers community",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });

//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}
