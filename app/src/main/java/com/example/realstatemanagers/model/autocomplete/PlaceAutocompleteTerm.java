package com.example.realstatemanagers.model.autocomplete;

public class PlaceAutocompleteTerm {

    Number offset;
    String value;

    public PlaceAutocompleteTerm(Number offset, String value) {
        this.offset = offset;
        this.value = value;
    }

    public Number getOffset() {
        return offset;
    }

    public String getValue() {
        return value;
    }
}
