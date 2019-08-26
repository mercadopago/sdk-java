package com.mercadopago.resources.datastructures.preference;

/**
 * Mercado Pago MercadoPago
 * Preference Tax class
 *
 * Created by Danilo Elias on 26/08/2019.
 */
public class Tax {

    private TaxType type = null;
    public enum TaxType {
        IVA,
        INC
    }
    private Float value = null;

    public TaxType getType() {
        return type;
    }

    public Float getValue() {
        return value;
    }

    public Tax setType(TaxType type) {
        this.type = type;
        return this;
    }

    public Tax setValue(Float value) {
        this.value = value;
        return this;
    }

}
