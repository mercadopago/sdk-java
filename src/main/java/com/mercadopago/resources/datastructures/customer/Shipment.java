package com.mercadopago.resources.datastructures.customer;

import java.util.ArrayList;

/**
 * Mercado Pago SDK
 * Customer Address Verification Shipment class
 */
public class Shipment {

    private Boolean success = null;
    private ArrayList<Error> errors = null;
    private String name = null;


    public Boolean getSuccess() {
        return success;
    }

    public ArrayList<Error> getErrors() {
        return errors;
    }

    public String getName() {
        return name;
    }

}
