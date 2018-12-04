package com.bgstuffs.pabapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class myLocation extends AppCompatActivity {

    SupportMapFragment supportMapFragment;

    FusedLocationProviderClient fusedLocationProviderClient;

    boolean isPermissionGranted = false;
    double latitude, longitude;
    Location lastLocation;
    GoogleMap goMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);

        supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.frag_map);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        checkLocationPermission();

        // for getting my current location by zooming it using compass kind of image on right top
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                goMap.clear();

                LatLng latLng = place.getLatLng();

                goMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(""+place.getName());
                goMap.addMarker(markerOptions);
            }

            @Override
            public void onError(Status status) {

            }
        });

    }

    public void initPlacePicker(View v)
    {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this),1);

        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                Place place = PlacePicker.getPlace(this,data);

                Toast.makeText(this, place.getName(), Toast.LENGTH_LONG).show();
                goMap.clear();
                LatLng newlatlng = place.getLatLng();

                goMap.moveCamera(CameraUpdateFactory.newLatLng(newlatlng));

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(newlatlng);
                markerOptions.title(""+place.getName());

                goMap.addMarker(markerOptions);
            }

        }
    }

    private void checkLocationPermission() {
        Log.d("TAG","checkLocationPermission: getting location permissions");

        // if permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            isPermissionGranted = true;
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    Log.d("Tag","in on success ");
                    if (location!=null){
                        lastLocation = location;
                        latitude = lastLocation.getLatitude();
                        longitude = lastLocation.getLongitude();

                        initMap();
                    }
                    else {
                        Log.d("Tag","Location in null ");
                    }
                }
            });
        }
        else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},2);
        }
    }

    private void initMap() {
        Log.d("TAG","initMap: initializing map");
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                goMap = googleMap;
                if (isPermissionGranted){

                    if (ActivityCompat.checkSelfPermission(myLocation.this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                        return;
                    }
                    goMap.setMyLocationEnabled(true);

                    LatLng latLng = new LatLng(latitude,longitude);
                    Log.d("Tag","latitude "+latitude);
                    Log.d("Tag","longitude "+longitude);

                    goMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    //goMap.setMyLocationEnabled(true);

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title("You are here");

                    goMap.addMarker(markerOptions);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("TAG","onRequestPermissionsResult: called");
        switch (requestCode){
            case 2:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    checkLocationPermission();
                else
                    Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
