package com.mercadopago.resources.datastructures.payment;

/**
 * Mercado Pago SDK
 * Fee Detail class
 */
public class FeeDetail {

    private Type type = null;
    public enum Type {
        mercadopago_fee,
        coupon_fee,
        financing_fee,
        shipping_fee,
        application_fee,
        discount_fee
    }
    private FeePayer feePayer = null;
    public enum FeePayer {
        collector,
        payer
    }
    private Float amount = null;


    public Type getType() {
        return type;
    }

    public FeePayer getFeePayer() {
        return feePayer;
    }

    public Float getAmount() {
        return amount;
    }

}
