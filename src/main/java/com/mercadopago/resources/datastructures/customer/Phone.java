package com.mercadopago.resources.datastructures.customer;

/**
 * Mercado Pago SDK
 * Customer Phone class
 */
public class Phone {

    private String areaCode = null;
    private String number = null;


    public String getAreaCode() {
        return areaCode;
    }

    public Phone setAreaCode(String areaCode) {
        this.areaCode = areaCode;
        return this;
    }

    public String getNumber() {
        return number;
    }

    public Phone setNumber(String number) {
        this.number = number;
        return this;
    }
}
