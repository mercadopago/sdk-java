package com.mercadopago.resources.datastructures.customer;

import java.lang.*;
import java.util.ArrayList;

/**
 * Mercado Pago SDK
 * Customer Address Verification Shipment class
 *
 * Created by Eduardo Paoletta on 12/15/16.
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
