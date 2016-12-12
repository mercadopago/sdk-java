package com.mercadopago.resources.datastructures.preferences;

import com.mercadopago.core.annotations.validation.Size;

/**
 * Mercado Pago SDK
 * Preferences Shipments AddressReceiver class
 *
 * Created by Eduardo Paoletta on 12/12/16.
 */
public class AddressReceiver extends Address {

    @Size(max=256) private String floor = null;
    @Size(max=256) private String apartment = null;


    @Override
    public AddressReceiver setZipCode(String zipCode) {
        return (AddressReceiver)super.setZipCode(zipCode);
    }

    @Override
    public AddressReceiver setStreetName(String streetName) {
        return (AddressReceiver)super.setStreetName(streetName);
    }

    @Override
    public AddressReceiver setStreetNumber(Integer streetNumber) {
        return (AddressReceiver)super.setStreetNumber(streetNumber);
    }

    public String getFloor() {
        return floor;
    }

    public AddressReceiver setFloor(String floor) {
        this.floor = floor;
        return this;
    }

    public String getApartment() {
        return apartment;
    }

    public AddressReceiver setApartment(String apartment) {
        this.apartment = apartment;
        return this;
    }

}