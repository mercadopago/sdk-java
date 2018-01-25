package com.mercadopago.resources.datastructures.preference;

import com.mercadopago.core.annotations.validation.Size;

/**
 * Mercado Pago SDK
 * Preference Payer Identification class
 *
 * Created by Eduardo Paoletta on 12/12/16.
 */
public class Identification {

    @Size(max=256) private String type = null;
    @Size(max=256) private String number = null;

    public void Identification(String type,String number){
        this.type = type;
        this.number = number;
    }


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
