package com.example.realstatemanagers.viewmodel;

import android.annotation.SuppressLint;
import android.location.Location;

import com.example.realstatemanagers.Possession;
import com.example.realstatemanagers.model.PermissionChecker;
import com.example.realstatemanagers.model.google.Place;
import com.example.realstatemanagers.repository.LocationRepository;
import com.example.realstatemanagers.repository.PossessionRepository;

import java.util.List;
import java.util.concurrent.Executor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class DetailViewModel extends ViewModel {
    PossessionRepository mPossessionRepository;
    LocationRepository mLocationRepository;
    PermissionChecker mPermissionChecker;
    Boolean hasGpsPermission = false;
    private Executor executor;

    public DetailViewModel(PossessionRepository possessionRepository, LocationRepository locationRepository, PermissionChecker permissionChecker, Executor executor) {
        mPossessionRepository = possessionRepository;
        mLocationRepository = locationRepository;
        mPermissionChecker = permissionChecker;
        this.executor = executor;
    }

    public void updatePhoto(Possession possession){

        executor.execute(() -> mPossessionRepository.updatePhoto(possession));
    }

    @SuppressLint("MissingPermission")
    public void refresh() {
        hasGpsPermission = mPermissionChecker.hasLocationPermission();

        if (hasGpsPermission) {
            mLocationRepository.startLocationRequest();
        } else {
            mLocationRepository.stopLocationRequest();
        }
    }
}
