package com.mercadopago.resources.datastructures.advancedpayment;

public class Identification {
    private String type;
    private String number;

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
