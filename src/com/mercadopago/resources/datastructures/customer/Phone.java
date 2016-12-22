package com.mercadopago.resources.datastructures.customer;

/**
 * Mercado Pago SDK
 * Customer Phone class
 *
 * Created by Eduardo Paoletta on 12/15/16.
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
