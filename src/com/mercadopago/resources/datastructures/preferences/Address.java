package com.mercadopago.resources.datastructures.preferences;

import com.mercadopago.core.annotations.validation.Size;

/**
 * Mercado Pago SDK
 * Preferences Payer Address class
 *
 * Created by Eduardo Paoletta on 12/12/16.
 */
public class Address {

    @Size(max=256) private String zipCode = null;
    @Size(max=256) private String streetName = null;
    private Integer streetNumber = null;


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

}
