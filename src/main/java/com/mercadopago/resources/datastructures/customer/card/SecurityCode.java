package com.mercadopago.resources.datastructures.customer.card;

/**
 * Mercado Pago SDK
 * Card Security Code class
 */
public class SecurityCode {

    private Integer length = null;
    private String cardLocation = null;


    public Integer getLength() {
        return length;
    }

    public SecurityCode setLength(Integer length) {
        this.length = length;
        return this;
    }

    public String getCardLocation() {
        return cardLocation;
    }

    public SecurityCode setCardLocation(String cardLocation) {
        this.cardLocation = cardLocation;
        return this;
    }

}
