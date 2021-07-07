package com.example.mechfinder;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.mechfinder.ui.home.HomeFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.GoogleApiAvailabilityCache;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class GetService extends AppCompatActivity implements OnMapReadyCallback {

    private FirebaseAuth mAuth;
    TextView userN;
    SupportMapFragment supportMapFragment;
    Button grEngage;
    GoogleMap map;
    GoogleApiClient mGoogleApiClient;
    String locality;
    Button go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (googleServiceAvailable()) {
            Toast.makeText(this, "Connected to google services", Toast.LENGTH_SHORT).show();
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            setContentView(R.layout.activity_get_service);
            initMap();
        } else {

        }
        mAuth = FirebaseAuth.getInstance();
        userN = findViewById(R.id.tv_user_info);

        go = findViewById(R.id.bt_service);

        mAuth.getCurrentUser().getEmail();
        mAuth.getCurrentUser().getDisplayName();
        userN.setText("Welcome: " + mAuth.getCurrentUser().getEmail());
        String locality = mAuth.getCurrentUser().getEmail();

    }

    private void initMap() {
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
    }

    public boolean googleServiceAvailable() {
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
    public void onBackPressed() {
        finish();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        map.setMyLocationEnabled(true);
        map = googleMap;
        goToLocationZoom(-0.002700, 34.611997, 15);
        double lat = -0.002700;
        double lng = 34.611997;

        goToLocationZoom(lat,lng, 15);
        MarkerOptions options = new MarkerOptions()
                .title("user")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                .position(new LatLng(lat, lng))
                .snippet("You are here");
        map.addMarker(options);

        double lat1 = 0.000746;
        double lng1 = 34.611721;
        MarkerOptions puncture = new MarkerOptions()
                .title("Omondi Puncture Repair")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.puncture))
                .position(new LatLng(lat1, lng1))
                .snippet("Quick Response 0712345676");
        map.addMarker(puncture);

        double lat2 = 0.002516;
        double lng2 = 34.609232;
        MarkerOptions Towing = new MarkerOptions()
                .title("Karanja Towing Services")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.tow))
                .position(new LatLng(lat2, lng2))
                .snippet("Call 072345687");
        map.addMarker(Towing);


        double lat3 = 0.005981;
        double lng3 = 34.611002;
        MarkerOptions engine = new MarkerOptions()
                .title("Kalonzo Engine Repair and Diagnosis")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.engine))
                .position(new LatLng(lat3, lng3))
                .snippet("Professional Touch: call 074765757");
        map.addMarker(engine);

        double lat4 = -0.004608;
        double lng4 = 34.608642;
        MarkerOptions simpleRepairs = new MarkerOptions()
                .title("Kimutai General Repair")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.breakdown))
                .position(new LatLng(lat4, lng4))
                .snippet("24hrs Available: call 0707254646");
        map.addMarker(simpleRepairs);

          }

    private void goToLocationZoom(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        map.moveCamera(update);
    }

    private void goToLocation(double lat, double lng) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
        map.moveCamera(update);
    }

    public void geoLocate(View view) throws IOException {
        Toast.makeText(this, locality,Toast.LENGTH_SHORT).show();



    }

    }
