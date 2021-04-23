package com.mercadopago.resources.datastructures.advancedpayment;

import com.google.gson.annotations.SerializedName;

public class ReceiverAddress {
    private String zipCode;
    private String streetName;
    @SerializedName("street_number")
    private String streetNumberString;
    private String floor;
    private String apartment;

    public String getZipCode() {
        return zipCode;
    }

    public ReceiverAddress setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public String getStreetName() {
        return streetName;
    }

    public ReceiverAddress setStreetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public Integer getStreetNumber() {
        if (streetNumberString == null) {
            return null;
        }
        try {
            return Integer.parseInt(streetNumberString, 10);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public ReceiverAddress setStreetNumber(Integer streetNumber) {
        if (streetNumber == null) {
            streetNumberString = null;
        } else {
            streetNumberString = streetNumber.toString();
        }
        return this;
    }

    public String getStreetNumberString() {
        return streetNumberString;
    }

    public ReceiverAddress setStreetNumberString(String streetNumberStr) {
        this.streetNumberString = streetNumberStr;
        return this;
    }

    public String getFloor() {
        return floor;
    }

    public ReceiverAddress setFloor(String floor) {
        this.floor = floor;
        return this;
    }

    public String getApartment() {
        return apartment;
    }

    public ReceiverAddress setApartment(String apartment) {
        this.apartment = apartment;
        return this;
    }

}
