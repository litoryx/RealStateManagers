package com.example.realstatemanagers.viewmodel;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import com.example.realstatemanagers.Possession;
import com.example.realstatemanagers.model.PermissionChecker;
import com.example.realstatemanagers.model.SearchCriteria;
import com.example.realstatemanagers.repository.LocationRepository;
import com.example.realstatemanagers.repository.PossessionRepository;
import com.example.realstatemanagers.repository.SearchRepository;
import com.example.realstatemanagers.repository.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    PossessionRepository DataSource;
    PermissionChecker mPermissionChecker;
    LocationRepository mLocationRepository;
    LiveData<List<Possession>> search;
    SearchRepository mSearchRepository;

    public MainViewModel(PossessionRepository dataSource, LocationRepository locationRepository, PermissionChecker permissionChecker,SearchRepository searchRepository) {
        DataSource = dataSource;
        mLocationRepository = locationRepository;
        mPermissionChecker = permissionChecker;
        mSearchRepository = searchRepository;

        LiveData<SearchCriteria> searchLiveData = searchRepository.getSearchTextLivedata();

        search = Transformations.switchMap(searchLiveData, searchText -> {
            if (searchText == null) {
                return DataSource.getPossession();
            } else {
                Log.d("search",searchText.toString());
                return DataSource.getPossessionQuery(searchText);
            }
        });
    }

    public LiveData<List<Possession>> getPossession(){ return search; }

    @SuppressLint("MissingPermission")
    public void refresh() {

        if (mPermissionChecker.hasLocationPermission()) {
            mLocationRepository.startLocationRequest();
        } else {
            mLocationRepository.stopLocationRequest();
        }
    }
}
