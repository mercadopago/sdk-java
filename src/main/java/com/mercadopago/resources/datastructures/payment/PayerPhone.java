package com.mercadopago.resources.datastructures.payment;

/**
 * Mercado Pago SDK
 * Phone Payer Payment   class
 *
 * Created by Eduardo Paoletta on 12/2/16.
 */
public class PayerPhone {

    private String areaCode = null;
    private String number = null;
    private String extension = null;

    public String getAreaCode() {
        return areaCode;
    }
    public String getNumber() {
        return number;
    }
    public String getExtension() {
        return extension;
    }

}
