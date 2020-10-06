package com.mercadopago.resources.datastructures.merchantorder;

import com.mercadopago.core.annotations.validation.Size;

/**
 * Mercado Pago SDK
 * Mechant Order Shipment Address class
 */
public class Address {

    @Size(max=256) private String zipCode = null;
    @Size(max=256) private String streetName = null;
    private Integer streetNumber = null;
    @Size(max=256) private String floor = null;
    @Size(max=256) private String apartment = null;


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

    public String getFloor() {
        return floor;
    }

    public Address setFloor(String floor) {
        this.floor = floor;
        return this;
    }

    public String getApartment() {
        return apartment;
    }

    public Address setApartment(String apartment) {
        this.apartment = apartment;
        return this;
    }

}
