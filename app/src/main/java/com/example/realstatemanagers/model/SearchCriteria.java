package com.example.realstatemanagers.model;

import androidx.annotation.NonNull;

public class SearchCriteria {

    String type_search;
    String date_beginS;
    String adresse;
    String agentSearch;
    int minNbrePiece;
    int maxNbrePiece;
    int minSurface;
    int maxSurface;
    int minPrice;
    int maxPrice;
    boolean sold;

    public SearchCriteria(String type_search, String date_beginS, String adresse, String agentSearch, int minNbrePiece, int maxNbrePiece, int minSurface, int maxSurface, int minPrice, int maxPrice, boolean sold) {
        this.type_search = type_search;
        this.date_beginS = date_beginS;
        this.adresse = adresse;
        this.agentSearch = agentSearch;
        this.minNbrePiece = minNbrePiece;
        this.maxNbrePiece = maxNbrePiece;
        this.minSurface = minSurface;
        this.maxSurface = maxSurface;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.sold = sold;
    }

    public String getType_search() {
        return type_search;
    }

    public String getDate_beginS() {
        return date_beginS;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getAgentSearch() {
        return agentSearch;
    }

    public int getMinNbrePiece() {
        return minNbrePiece;
    }

    public int getMaxNbrePiece() {
        return maxNbrePiece;
    }

    public int getMinSurface() {
        return minSurface;
    }

    public int getMaxSurface() {
        return maxSurface;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public boolean isSold() {
        return sold;
    }

    @NonNull
    @Override
    public String toString() {
        return "SearchCriteria{" +
                "type_search='" + type_search + '\'' +
                ", date_beginS='" + date_beginS + '\'' +
                ", adresse='" + adresse + '\'' +
                ", agentSearch='" + agentSearch + '\'' +
                ", minNbrePiece=" + minNbrePiece +
                ", maxNbrePiece=" + maxNbrePiece +
                ", minSurface=" + minSurface +
                ", maxSurface=" + maxSurface +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", sold=" + sold +
                '}';
    }
}
