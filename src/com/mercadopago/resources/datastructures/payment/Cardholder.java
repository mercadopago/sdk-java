package com.mercadopago.resources.datastructures.payment;

/**
 * Mercado Pago SDK
 * Cardholder class
 *
 * Created by Eduardo Paoletta on 12/2/16.
 */
public class Cardholder {

    private String name = null;
    private Identification identification = null;


    public String getName() {
        return name;
    }

    public Identification getIdentification() {
        return identification;
    }

}
