package com.example.realstatemanagers.viewmodel;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.realstatemanagers.Possession;
import com.example.realstatemanagers.model.PermissionChecker;
import com.example.realstatemanagers.repository.LocationRepository;
import com.example.realstatemanagers.repository.PossessionRepository;
import com.example.realstatemanagers.repository.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    PossessionRepository DataSource;
    PermissionChecker mPermissionChecker;
    LocationRepository mLocationRepository;
    LiveData<List<Possession>> search;

    @Nullable
    String location;

    public MainViewModel(PossessionRepository dataSource, LocationRepository locationRepository, PermissionChecker permissionChecker) {
        DataSource = dataSource;
        mLocationRepository = locationRepository;
        mPermissionChecker = permissionChecker;

        search = DataSource.getPossession();

        mLocationRepository.getLocationLiveData().observeForever(s -> {
            location = s;

        });
    }

    public LiveData<List<Possession>> getPossession(){ return search; }

    public LiveData<List<Possession>> updateSearchText(String text){
        Log.d("geoview",""+text);

        search = DataSource.getPossessionQuery(text);

        Log.d("retourSearch",""+search);
        return search;
    }

    @SuppressLint("MissingPermission")
    public void refresh() {

        if (mPermissionChecker.hasLocationPermission()) {
            mLocationRepository.startLocationRequest();
        } else {
            mLocationRepository.stopLocationRequest();
        }
    }
}
