package com.mercadopago.resources.datastructures.advancedpayment;

public class Phone {
    private String areaCode;
    private String number;
    private String extension;

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

    public String getExtension() {
        return extension;
    }

    public Phone setExtension(String extension) {
        this.extension = extension;
        return this;
    }


}
