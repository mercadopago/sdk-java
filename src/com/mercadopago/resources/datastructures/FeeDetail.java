package com.mercadopago.resources.datastructures;

/**
 * Mercado Pago SDK
 * Fee Detail class
 *
 * Created by Eduardo Paoletta on 12/2/16.
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
    private FeePayer fee_payer = null;
    public enum FeePayer {
        collector,
        payer
    }
    private Float fee_amount = null;

    public Type getType() {
        return type;
    }

    public FeePayer getFee_payer() {
        return fee_payer;
    }

    public Float getFee_amount() {
        return fee_amount;
    }

}
