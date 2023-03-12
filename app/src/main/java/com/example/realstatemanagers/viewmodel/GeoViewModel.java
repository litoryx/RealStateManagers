package com.example.realstatemanagers.viewmodel;

import android.annotation.SuppressLint;
import android.location.Location;
import android.util.Log;

import com.example.realstatemanagers.Possession;
import com.example.realstatemanagers.model.GeoViewState;
import com.example.realstatemanagers.model.PermissionChecker;
import com.example.realstatemanagers.model.autocomplete.Prediction;
import com.example.realstatemanagers.model.google.Place;
import com.example.realstatemanagers.repository.AutoCompleteRepository;
import com.example.realstatemanagers.repository.LocationRepository;
import com.example.realstatemanagers.repository.PossessionRepository;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class GeoViewModel extends ViewModel {

    LocationRepository mLocationRepository;
    PossessionRepository mPossessionRepository;
    AutoCompleteRepository mAutoCompleteRepository;
    PermissionChecker mPermissionChecker;
    Boolean hasGpsPermission = false;
    private final MediatorLiveData<List<GeoViewState>> mGeoViewStateLiveData = new MediatorLiveData<>();
    LiveData<String> locationLiveData;
    LiveData<Location> locationLiveDataLoc;

    public GeoViewModel(LocationRepository locationRepository, PermissionChecker permissionChecker, PossessionRepository possessionRepository, AutoCompleteRepository autoCompleteRepository) {
        mLocationRepository = locationRepository;
        mPermissionChecker = permissionChecker;
        mPossessionRepository = possessionRepository;
        mAutoCompleteRepository = autoCompleteRepository;

        locationLiveData = locationRepository.getLocationLiveData();
        locationLiveDataLoc = locationRepository.getLocationLiveDataFormatLoc();
        LiveData<List<Possession>> possessions = possessionRepository.getPossession();
        LiveData<List<Prediction>> predictionsLiveData = autoCompleteRepository.getListPredictionLiveData();

        LiveData<List<Place>> nearbyPlacesLivedata = Transformations.switchMap(locationLiveData, location ->
                mPossessionRepository.fetchPIFollowing(location));

        mGeoViewStateLiveData.addSource(nearbyPlacesLivedata, places ->
                combine(places, predictionsLiveData.getValue())
        );

        mGeoViewStateLiveData.addSource(predictionsLiveData, predictionList ->
                combine(nearbyPlacesLivedata.getValue(), predictionList)
        );
    }

    private void combine(@Nullable List<Place> places,  @Nullable List<Prediction> predictions) {
        // Si places ou users est null, cela veut dire qu'on a pas encore reçu la réponse à nos
        // différentes requêtes. On ne peut pas calculer le GeoViewState donc on stoppe l'exécution
        // de cette fonction en appellant "return"
        if (places == null) {
            Log.d("GeoviewModel","places=null");
            return;
        }
        Log.d("GeoviewModel", places.size()+"");
        List<GeoViewState> mGeoList = new ArrayList<>();

        // On transforme les places en GeoViewState
        for (Place place : places) {
            GeoViewState mGeoViewState = new GeoViewState(
                    place.getGeometry().getLatLngLiteral().getLat(),
                    place.getGeometry().getLatLngLiteral().getLng());

            //Verifie si il est dans la liste Predictions
            if(predictions == null){mGeoList.add(mGeoViewState);
                Log.d("GeoviewModel", mGeoList.size()+" GeoViewState");
            }else{
                if(TrueorFalsePredictions(predictions, place.getPlace_id())){
                    mGeoList.add(mGeoViewState);
                    Log.d("GeoviewModel", mGeoList.size()+" GeoViewState");
                }
            }
        }
        mGeoViewStateLiveData.setValue(mGeoList);
    }

    private Boolean TrueorFalsePredictions(List<Prediction> predictions, String placeId){
        for (Prediction prediction:predictions){
            if(placeId.equals(prediction.getIdPred())){return true;}
        }
        return false;
    }

    public LiveData<List<GeoViewState>> getViewStateLiveData() {
        return mGeoViewStateLiveData;
    }

    @Nullable
    public Location getUserLocation(){

        return locationLiveDataLoc.getValue();
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
