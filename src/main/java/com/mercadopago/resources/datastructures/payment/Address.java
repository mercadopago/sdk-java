package com.mercadopago.resources.datastructures.payment;

import com.google.gson.annotations.SerializedName;
import com.mercadopago.core.annotations.validation.Size;

/**
 * Mercado Libre SDK
 * Address class
 */
public class Address {


    @Size(max=256) private String zipCode = null;
    @Size(max=256) private String streetName = null;
    private String neighborhood = null;
    private String city = null;
    private String federalUnit = null;
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

    public String getNeighborhood() {
        return neighborhood;
    }

    public Address setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Address setCity(String city) {
        this.city = city;
        return this;
    }

    public String getFederalUnit() {
        return federalUnit;
    }

    public Address setFederalUnit(String federalUnit) {
        this.federalUnit = federalUnit;
        return this;
    }
}
