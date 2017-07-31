package com.mercadopago.resources.datastructures.customer.card;

/**
 * Mercado Pago SDK
 * Card Issuer class
 *
 * Created by Eduardo Paoletta on 12/15/16.
 */
public class Issuer {

    private String id = null;
    private String name = null;


    public String getId() {
        return id;
    }

    public Issuer setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Issuer setName(String name) {
        this.name = name;
        return this;
    }

}
