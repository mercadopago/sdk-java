package com.mercadopago.resources.datastructures.payment;

import com.mercadopago.core.annotations.validation.Size;

import org.checkerframework.checker.units.qual.degrees;

public class AddressReceiver {

    private String zipCode = null;
    private String stateName = null;
    private String cityName = null;
    private Integer streetNumber = null;
    private String streetName = null;
    @Size(max=256) private String floor = null;
    @Size(max=256) private String apartment = null;

    public AddressReceiver setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public String getZipCode() {
        return zipCode;
    }
    
    public AddressReceiver setStateName(String stateName) {
        this.stateName = stateName;
        return this;
    }
    
    public String getStateName() {
        return stateName;
    }

    public AddressReceiver setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public String getCityName() {
        return cityName;
    }

    public AddressReceiver setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
        return this;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public AddressReceiver setStreetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public String getStreetName() {
        return streetName;
    }

    public AddressReceiver setFloor(String floor) {
        this.floor = floor;
        return this;
    }

    public String getFloor() {
        return floor;
    }

    public AddressReceiver setApartment(String apartment) {
        this.apartment = apartment;
        return this;
    }

    public String getApartment() {
        return apartment;
    }
}
