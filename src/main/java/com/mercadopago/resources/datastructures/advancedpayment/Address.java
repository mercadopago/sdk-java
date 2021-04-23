package com.mercadopago.resources.datastructures.advancedpayment;

import com.google.gson.annotations.SerializedName;

public class Address {
    private String zipCode;
    private String streetName;
    @SerializedName("street_number")
    private String streetNumberString;

    public String getZipCode() {
        return zipCode;
    }

    public Address setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public String getStreetName() {
        return streetName;
    }

    public Address setStreetName(String streetName) {
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

    public Address setStreetNumber(Integer streetNumber) {
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

    public Address setStreetNumberString(String streetNumberStr) {
        this.streetNumberString = streetNumberStr;
        return this;
    }

}
