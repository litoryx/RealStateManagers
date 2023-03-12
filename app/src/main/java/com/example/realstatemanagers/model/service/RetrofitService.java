package com.example.realstatemanagers.model.service;

import com.example.realstatemanagers.model.service.GooglePlacesService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static GooglePlacesService getnetService(){return retrofit.create(GooglePlacesService.class);}

    public static GooglePlacesService getACService(){return retrofit.create(GooglePlacesService.class);}
}
