package com.example.realstatemanagers.model.service;

import com.example.realstatemanagers.model.autocomplete.PlacesAutocompleteResponse;
import com.example.realstatemanagers.model.google.PlacesNearbySearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GooglePlacesService {
    @GET("api/place/nearbysearch/json")
    Call<PlacesNearbySearchResponse> getFollowing(@Query("location") String location,
                                                  @Query("radius") int radius,
                                                  @Query("type") String type,
                                                  @Query("key") String key);

    @GET("api/place/autocomplete/json")
    Call<PlacesAutocompleteResponse> getPredictions(@Query("input") String input,
                                                    @Query("location")String location,
                                                    @Query("radius") int radius,
                                                    @Query("types") String types,
                                                    @Query("key") String key);
}
