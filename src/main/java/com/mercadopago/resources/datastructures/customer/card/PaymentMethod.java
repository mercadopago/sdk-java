package com.mercadopago.resources.datastructures.customer.card;

/**
 * Mercado Pago SDK
 * Card Payment Method class
 */
public class PaymentMethod {

    private String id = null;
    private String name = null;
    private String paymentTypeId = null;
    private String thumbnail = null;
    private String secureThumbnail = null;


    public String getId() {
        return id;
    }

    public PaymentMethod setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PaymentMethod setName(String name) {
        this.name = name;
        return this;
    }

    public String getPaymentTypeId() {
        return paymentTypeId;
    }

    public PaymentMethod setPaymentTypeId(String paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
        return this;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public PaymentMethod setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public String getSecureThumbnail() {
        return secureThumbnail;
    }

    public PaymentMethod setSecureThumbnail(String secureThumbnail) {
        this.secureThumbnail = secureThumbnail;
        return this;
    }

}
