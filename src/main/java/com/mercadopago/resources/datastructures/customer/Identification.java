package com.mercadopago.resources.datastructures.customer;

/**
 * Mercado Pago SDK
 * Customer Identification class
 */
public class Identification {

    private String type = null;
    private String number = null;


    public String getType() {
        return type;
    }

    public Identification setType(String type) {
        this.type = type;
        return this;
    }

    public String getNumber() {
        return number;
    }

    public Identification setNumber(String number) {
        this.number = number;
        return this;
    }

}
