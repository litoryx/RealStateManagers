package com.example.realstatemanagers;

import android.content.Context;
import android.os.Looper;

import com.example.realstatemanagers.model.PermissionChecker;
import com.example.realstatemanagers.MainApplication;
import com.example.realstatemanagers.repository.AutoCompleteRepository;
import com.example.realstatemanagers.repository.LocationRepository;
import com.example.realstatemanagers.repository.PossessionRepository;
import com.example.realstatemanagers.repository.SavePosMyDataBase;
import com.example.realstatemanagers.repository.SearchRepository;
import com.example.realstatemanagers.viewmodel.AddViewModel;
import com.example.realstatemanagers.viewmodel.DetailViewModel;
import com.example.realstatemanagers.viewmodel.GeoViewModel;
import com.example.realstatemanagers.viewmodel.MainViewModel;
import com.example.realstatemanagers.viewmodel.SearchViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MainViewModelFactory implements ViewModelProvider.Factory {

    private static MainViewModelFactory factory;
    Looper mLooper;

    public static MainViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (MainViewModelFactory.class) {
                if (factory == null) {
                    factory = new MainViewModelFactory();
                }
            }
        }
        return factory;
    }

    FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(MainApplication.getApplication());
    private final LocationRepository mLocationRepository = new LocationRepository(locationClient, mLooper);
    private final PermissionChecker mPermissionChecker = new PermissionChecker(MainApplication.getApplication());
    private final AutoCompleteRepository mAutoCompleteRepository = AutoCompleteRepository.getInstance();
    SavePosMyDataBase database = SavePosMyDataBase.getInstance(MainApplication.getApplication());
    private final PossessionRepository DataSe = new PossessionRepository(database.mPossessionDAO());
    private final SearchRepository mSearchRepository = SearchRepository.getInstance();
    Executor executor = Executors.newSingleThreadExecutor();

    private MainViewModelFactory() {
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(DataSe, mLocationRepository, mPermissionChecker, mSearchRepository);
        }else if(modelClass.isAssignableFrom(AddViewModel.class)){
            return (T) new AddViewModel(DataSe, executor);
        }else if(modelClass.isAssignableFrom(GeoViewModel.class)){
            return (T) new GeoViewModel(mLocationRepository, mPermissionChecker ,DataSe, mAutoCompleteRepository);
        }else if(modelClass.isAssignableFrom(SearchViewModel.class)){
            return (T) new SearchViewModel(mSearchRepository);
        }else if(modelClass.isAssignableFrom(DetailViewModel.class)) {
            return (T) new DetailViewModel(DataSe, mLocationRepository, mPermissionChecker, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
