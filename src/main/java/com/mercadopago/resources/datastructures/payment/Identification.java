package com.mercadopago.resources.datastructures.payment;

import com.mercadopago.core.annotations.validation.Size;

/**
 * Mercado Pago SDK
 * Identification class
 */
public class Identification {

    @Size(max=256) private String type = null;
    @Size(max=256) private String number = null;


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
