package com.example.realstatemanagers.model;

public class GeoViewState {
    private Double lat;
    private Double lng;


    public GeoViewState(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }



    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }
}
