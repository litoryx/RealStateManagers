package com.example.realstatemanagers.repository;

import android.util.Log;

import com.example.realstatemanagers.BuildConfig;
import com.example.realstatemanagers.Possession;
import com.example.realstatemanagers.model.service.RetrofitService;
import com.example.realstatemanagers.model.google.Place;
import com.example.realstatemanagers.model.google.PlacesNearbySearchResponse;
import com.example.realstatemanagers.model.service.GooglePlacesService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PossessionRepository {

    PossessionDAO mPossessionDAO;
    GooglePlacesService googleService;
    Map<String, List<Place>> mCache = new HashMap<>();
    LiveData<List<Possession>> possessions;

    public PossessionRepository(PossessionDAO possessionDAO){this.mPossessionDAO = possessionDAO;}

    public LiveData<List<Possession>> getPossession(){ return this.mPossessionDAO.getPossession();}

    public LiveData<List<Possession>> getPossessionQuery(String possessionId){
        Log.d("QueryPoss",""+possessionId);

        String[] possessionList = possessionId.split(" ");

            if (possessionList.length >= 2) {
                possessions = mPossessionDAO.getPossessionsAsk(
                        new SimpleSQLiteQuery("SELECT * FROM Possession WHERE id = ? OR type_bien = ? OR val_bien = ? OR surface = ? OR nbre_piece = ? OR adr = ?", possessionList));
                return possessions;
            } else {
                return mPossessionDAO.getPossessionsAsk(
                        new SimpleSQLiteQuery("SELECT * FROM Possession WHERE id = ? OR type_bien = ? OR val_bien = ? OR surface = ? OR nbre_piece = ? OR adr = ?", new String[]{possessionId, possessionId, possessionId, possessionId, possessionId, possessionId}));
            }

    }

    public void insertPossession(Possession possession){mPossessionDAO.InsertPoss(possession);}

    public void updatePossession(Possession possession){mPossessionDAO.updatePossession(possession);}


    public LiveData<List<Place>> fetchPIFollowing(String location){

        MutableLiveData<List<Place>> nearby = new MutableLiveData<>();

        // 2.2 - Get a Retrofit instance and the related endpoints
        googleService = RetrofitService.getnetService();

        if(mCache.get(location) != null){
            nearby.setValue(mCache.get(location));
        }else {

            // 2.3 - Create the call on Github API
            Call<PlacesNearbySearchResponse> call = googleService.getFollowing(location, 1500, "point_of_interest", BuildConfig.MAPS_API_KEY);
            // 2.4 - Start the call
            call.enqueue(new Callback<PlacesNearbySearchResponse>() {

                @Override
                public void onResponse(@NonNull Call<PlacesNearbySearchResponse> call, @NonNull Response<PlacesNearbySearchResponse> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        nearby.setValue(response.body().getPlaces());
                        mCache.put(location, response.body().getPlaces());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PlacesNearbySearchResponse> call, @NonNull Throwable t) {
                    nearby.setValue(null);
                    Log.d("NetRepository", "Failure getfollowing"+t.getMessage());
                    t.printStackTrace();
                }
            });
        }
        return nearby;
    }
}
