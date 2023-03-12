package com.example.realstatemanagers.model.google;

public class Place {

    String place_id;
    String name;
    String url;
    String vicinity;
    Geometry geometry;

    public Place(String place_id, String name, String url, String vicinity) {
        this.place_id = place_id;
        this.name = name;
        this.url = url;
        this.vicinity = vicinity;
    }

    public String getPlace_id() {
        return place_id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getVicinity() {
        return vicinity;
    }

    public Geometry getGeometry() {
        return geometry;
    }
}
