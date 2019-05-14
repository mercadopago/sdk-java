package com.mercadopago.resources.datastructures.customer.card;

/**
 * Mercado Pago SDK
 * Card Cardholder Identification class
 *
 * Created by Eduardo Paoletta on 12/15/16.
 */
public class Identification {

    private String number = null;
    private String subtype = null;
    private String type = null;


    public String getNumber() {
        return number;
    }

    public Identification setNumber(String number) {
        this.number = number;
        return this;
    }

    public String getSubtype() {
        return subtype;
    }

    public Identification setSubtype(String subtype) {
        this.subtype = subtype;
        return this;
    }

    public String getType() {
        return type;
    }

    public Identification setType(String type) {
        this.type = type;
        return this;
    }

}
