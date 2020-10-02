package com.mercadopago.resources.datastructures.customer.card;

/**
 * Mercado Pago SDK
 * Card Cardholder class
 */
public class Cardholder {

    private String name = null;
    private Identification identification = null;


    public String getName() {
        return name;
    }

    public Cardholder setName(String name) {
        this.name = name;
        return this;
    }

    public Identification getIdentification() {
        return identification;
    }

    public Cardholder setIdentification(Identification identification) {
        this.identification = identification;
        return this;
    }

}
