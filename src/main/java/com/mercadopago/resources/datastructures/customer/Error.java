package com.mercadopago.resources.datastructures.customer;

/**
 * Mercado Libre SDK
 * Customer Address Verification Shipment Error class
 */
public class Error {

    private String code = null;
    private String description = null;
    private String field = null;


    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getField() {
        return field;
    }

}
