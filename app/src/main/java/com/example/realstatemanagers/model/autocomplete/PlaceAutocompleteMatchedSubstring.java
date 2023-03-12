package com.example.realstatemanagers.model.autocomplete;

public class PlaceAutocompleteMatchedSubstring {

    Number length;
    Number offset;

    public PlaceAutocompleteMatchedSubstring(Number length, Number offset) {
        this.length = length;
        this.offset = offset;
    }


    public Number getLength() {
        return length;
    }

    public Number getOffset() {
        return offset;
    }
}
