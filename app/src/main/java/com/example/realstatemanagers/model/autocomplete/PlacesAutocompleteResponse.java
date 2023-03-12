package com.example.realstatemanagers.model.autocomplete;

import java.util.List;

public class PlacesAutocompleteResponse {

    List<PlaceAutocompletePrediction> predictions;

    public PlacesAutocompleteResponse(List<PlaceAutocompletePrediction> predictions) {
        this.predictions = predictions;
    }

    public List<PlaceAutocompletePrediction> getPredictions() {
        return predictions;
    }

}
