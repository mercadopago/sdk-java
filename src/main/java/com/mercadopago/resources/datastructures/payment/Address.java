package com.mercadopago.resources.datastructures.payment;

import com.mercadopago.core.annotations.validation.Size;

/**
 * Mercado Libre SDK
 * Address class
 *
 * Created by Eduardo Paoletta on 11/9/16.
 */
public class Address {


    @Size(max=256) private String zipCode = null;
    @Size(max=256) private String streetName = null;
    private Integer streetNumber = null;
    private String neighborhood = null;
    private String city = null;
    private String federalUnit = null;

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
        return streetNumber;
    }

    public Address setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
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
