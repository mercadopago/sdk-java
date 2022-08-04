package com.mercadopago.resources.datastructures.payment;

/**
 * Mercado Pago MercadoPago
 * Payment Tax class
 */
public class Tax {

    private TaxType type = null;
    private Float value = null;
    private Boolean percentage = null;

    public TaxType getType() {
        return type;
    }

    public Float getValue() {
        return value;
    }

    public Boolean getPercentage() {
        return percentage;
    }

    public Tax setType(TaxType type) {
        this.type = type;
        return this;
    }

    public Tax setValue(Float value) {
        this.value = value;
        return this;
    }

    public Tax setPercentage(Boolean percentage) {
        this.percentage = percentage;
        return this;
    }

    public enum TaxType {
        IVA,
        INC
    }
}
