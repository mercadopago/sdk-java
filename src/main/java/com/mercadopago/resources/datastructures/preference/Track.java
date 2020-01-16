package com.mercadopago.resources.datastructures.preference;

public class Track {

    private String type;
    private TrackValues values;

    public String getType() {
        return type;
    }

    public Track setType(String type) {
        this.type = type;
        return this;
    }

    public TrackValues getValues() {
        return values;
    }

    public Track setValues(TrackValues values) {
        this.values = values;
        return this;
    }
}
