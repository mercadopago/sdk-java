package com.mercadopago.resources.datastructures.payment;

import com.google.gson.JsonObject;

import java.util.Date;

/**
 * Mercado Pago SDK
 * Refund class
 * Created by Eduardo Paoletta on 12/2/16.
 */
public class Refund {

    private Number id = null;
    private Number paymentId = null;
    private Float amount = null;
    private JsonObject metadata = null;
    private Source source = null;
    private Date dateCreated = null;
    private String uniqueSequenceNumber = null;


    public Number getId() {
        return id;
    }

    public Number getPaymentId() {
        return paymentId;
    }

    public Float getAmount() {
        return amount;
    }

    public JsonObject getMetadata() {
        return metadata;
    }

    public Source getSource() {
        return source;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public String getUniqueSequenceNumber() {
        return uniqueSequenceNumber;
    }

}
