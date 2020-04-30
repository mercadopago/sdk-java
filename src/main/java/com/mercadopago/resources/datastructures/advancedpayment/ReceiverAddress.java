package com.mercadopago.resources.datastructures.advancedpayment;

public class ReceiverAddress {
    private String zipCode;
    private String streetName;
    private int streetNumber;
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

    public int getStreetNumber() {
        return streetNumber;
    }

    public ReceiverAddress setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
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
