package com.mercadopago.resources.datastructures;

import com.mercadopago.core.annotations.validation.Size;

/**
 * Mercado Pago SDK
 * Receiver address class
 *
 * Created by Eduardo Paoletta on 11/9/16.
 */
public class ReceiverAddress extends Address {

    @Size(max=256) private String floor = null;
    @Size(max=256) private String apartment = null;

    @Override
    public ReceiverAddress setZipCode(String zipCode) {
        return (ReceiverAddress)super.setZipCode(zipCode);
    }

    @Override
    public ReceiverAddress setStreetName(String streetName) {
        return (ReceiverAddress)super.setStreetName(streetName);
    }

    @Override
    public ReceiverAddress setStreetNumber(Integer streetNumber) {
        return (ReceiverAddress)super.setStreetNumber(streetNumber);
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
