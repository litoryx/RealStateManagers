package com.example.realstatemanagers.model.google;

import java.util.List;

public class PlacesNearbySearchResponse {
    List<Place> results;

    public PlacesNearbySearchResponse(List<Place> results) {
        this.results = results;
    }

    public List<Place> getPlaces() {
        return results;
    }
}
