package com.felohscodes.onroadmech;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MechanicsMap extends AppCompatActivity implements OnMapReadyCallback {
    SupportMapFragment fragment;
    GoogleMap map;
    TextView user;
    Button engage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ServiceAvailable()) {
            Toast.makeText(this, "Connected to google services", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_mecahics_map);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            showMechMap();
        } else {

        }
        engage = findViewById(R.id.bt_engage);
        engage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MechanicsMap.this, Login.class);
                startActivity(intent);
            }
        });
//        auth = FirebaseAuth.getInstance();
//
//        user = findViewById(R.id.tv_user);
//        String noUser = "You Are Not Logged In";
//        if (auth.getCurrentUser().FirebaseUser.getEmail() == null) {
//            user.setText(noUser);
//        }else{
//            user.setText("User: " + auth.getCurrentUser().getEmail());
//        }

    }

    private void showMechMap() {
        fragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mech_map);
        fragment.getMapAsync((OnMapReadyCallback) this);
    }
    public boolean ServiceAvailable() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int isAvailable = apiAvailability.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (apiAvailability.isUserResolvableError(isAvailable)) {
            Dialog dialog = apiAvailability.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Cannot Connect to play Services", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        loadLocation(-0.001978, 34.612429, 16);
        double lat = -0.001978;
        double lng = 34.612429;
        loadLocation(lat,lng, 15);
        MarkerOptions userLocation = new MarkerOptions()
                .title("Onroad Mech")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                .position(new LatLng(lat, lng))
                .snippet("User Location");
        map.addMarker(userLocation);

        double lat1 = -0.005132;
        double lng1 = 34.611166;
        loadLocation(lat1,lng1, 15);
        MarkerOptions masenoMech = new MarkerOptions()
                .title("Maseno Garage")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.mechanic_foreground))
                .position(new LatLng(lat1, lng1))
                .snippet("Contact: 0793537454");
        map.addMarker(masenoMech);

        double lat2 = -0.008257;
        double lng2 = 34.610697;
        loadLocation(lat2,lng2, 15);
        MarkerOptions orangeMech = new MarkerOptions()
                .title("Orange House Garage")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.mechanic_foreground))
                .position(new LatLng(lat2, lng2))
                .snippet("Contact: 0163564764");
        map.addMarker(orangeMech);

        double lat3 = 0.000229;
        double lng3 = 34.611132;
        loadLocation(lat3,lng3, 15);
        MarkerOptions mabungoMech = new MarkerOptions()
                .title("Mabungo Garage")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.mechanic_foreground))
                .position(new LatLng(lat3, lng3))
                .snippet("Contact: 0711286454");
        map.addMarker(mabungoMech);

        double lat4 = -0.002641;
        double lng4 = 34.609875;
        loadLocation(lat4,lng4, 15);
        MarkerOptions fieldMech = new MarkerOptions()
                .title("School Garage")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.mechanic_foreground))
                .position(new LatLng(lat4, lng4))
                .snippet("Contact: 0786454643");
        map.addMarker(fieldMech);

        double lat5 = -0.002641;
        double lng5 = 34.609875;
        loadLocation(lat5,lng5, 15);
        MarkerOptions nyawitaMech = new MarkerOptions()
                .title("Nyawita Garage")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.mechanic_foreground))
                .position(new LatLng(lat5, lng5))
                .snippet("Contact: 0756445463");
        map.addMarker(nyawitaMech);

        double lat6 = -0.002006;
        double lng6 = 34.612400;
        loadLocation(lat6,lng6, 15);
        MarkerOptions newMech = new MarkerOptions()
                .title("New Mech")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.mechanic_foreground))
                .position(new LatLng(lat6, lng6))
                .snippet("Contact: 0792567464");
        map.addMarker(newMech);


    }

    private void loadLocation(double lat, double lng, int focus) {
        LatLng newLocation = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(newLocation, focus);
        map.moveCamera(update);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_type, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mapTypeNone:
                map.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.mapTypeNormal:
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapTypeSatellite:
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.mapTypeTerrain:
                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.mapTypeHybrid:
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}