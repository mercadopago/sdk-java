package com.mercadopago.resources.datastructures;

import com.mercadopago.core.validationannotations.Size;

/**
 * Mercado Libre SDK
 * Address class
 *
 * Created by Eduardo Paoletta on 11/9/16.
 */
public class Address {

    @Size(max=256) private String zip_code = null;
    @Size(max=256) private String street_name = null;
    private Integer street_number = null;

    public String getZipCode() {
        return zip_code;
    }

    public Address setZipCode(String zipCode) {
        this.zip_code = zipCode;
        return this;
    }

    public String getStreetName() {
        return street_name;
    }

    public Address setStreetName(String streetName) {
        this.street_name = streetName;
        return this;
    }

    public Integer getStreetNumber() {
        return street_number;
    }

    public Address setStreetNumber(Integer streetNumber) {
        this.street_number = streetNumber;
        return this;
    }

}
