package com.mercadopago.resources.datastructures.preference;

import com.mercadopago.core.annotations.validation.Size;

/**
 * Mercado Pago SDK
 * Preference Payer Phone class
 */
public class Phone {

    @Size(max=256) private String areaCode = null;
    @Size(max=256) private String number = null;

    public void Phone(String areaCode,String number){
        this.areaCode = areaCode;
        this.number = number;
    }


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
