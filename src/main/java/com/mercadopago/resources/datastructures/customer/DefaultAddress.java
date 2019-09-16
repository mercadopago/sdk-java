package com.mercadopago.resources.datastructures.customer;

/**
 * Mercado Pago SDK
 * Customer Address digest class
 *
 * Created by Eduardo Paoletta on 12/15/16.
 */
public class DefaultAddress {

    private String id = null;
    private String zipCode = null;
    private String streetName = null;
    private Integer streetNumber = null;


    public String getId() {
        return id;
    }

    public String getZipCode() {
        return zipCode;
    }

    public DefaultAddress setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public String getStreetName() {
        return streetName;
    }

    public DefaultAddress setStreetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public DefaultAddress setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
        return this;
    }

}
