package com.example.realstatemanagers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.realstatemanagers.model.GeoViewState;
import com.example.realstatemanagers.viewmodel.GeoViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class GeoActivity extends AppCompatActivity implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMarkerClickListener{

    GeoViewModel mGeoViewModel;
    FloatingActionButton mFloatingActionButton;
    GoogleMap map;
    Location loc = new Location("1.0,0.0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo);
        mFloatingActionButton = findViewById(R.id.center_buttonMap);

        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mGeoViewModel = new ViewModelProvider(this, MainViewModelFactory.getInstance()).get(GeoViewModel.class);

        assert fragment != null;
        fragment.getMapAsync( this);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                0
        );
    }


    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        Integer clickCount = (Integer) marker.getTag();

        if(clickCount != null){marker.getTitle();}

        return false;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return true;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        mGeoViewModel.getViewStateLiveData().observe(this, geoViewState -> {
            map.clear();
            for (GeoViewState viewState : geoViewState) {
                LatLng mCoord = new LatLng(viewState.getLat(), viewState.getLng());
                Log.d("Coord", mCoord+"");

                map.addMarker(new MarkerOptions()
                        .position(mCoord));

                map.addMarker(new MarkerOptions()
                        .position(mCoord)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            }

            loc = mGeoViewModel.getUserLocation();

            if(loc != null) {
                LatLng finalMCoord = new LatLng(loc.getLatitude(), loc.getLongitude());
                float zoomLevel = 16f;

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(finalMCoord, zoomLevel));

            }
        });


        mFloatingActionButton.setOnClickListener(view -> {

            loc = mGeoViewModel.getUserLocation();

            if(loc != null) {
                LatLng finalMCoord = new LatLng(loc.getLatitude(), loc.getLongitude());
                map.moveCamera(CameraUpdateFactory.newLatLng(finalMCoord));
            }

        });

        map.setOnMarkerClickListener(this);
    }


    @Override
    public void onResume() {
        super.onResume();

        mGeoViewModel.refresh();
    }
}