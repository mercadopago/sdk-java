package com.mercadopago.resources.datastructures;

import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Mercado Pago SDK
 * Refund class
 * Created by Eduardo Paoletta on 12/2/16.
 */
public class Refund {

    private Number id = null;
    private Number payment_id = null;
    private Float amount = null;
    private JsonObject metadata = null;
    private Source source = null;

    public Number getId() {
        return id;
    }

    public Number getPayment_id() {
        return payment_id;
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
}
