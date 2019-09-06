package com.mercadopago.resources;

import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.MPResourceArray;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.datastructures.payment.Source;

import java.util.Date;

public class Refund extends MPBase {

    private String id = null;

    private transient String paymentId = null;

    private Float amount = null;

    private Source source = null;

    private Date dateCreated = null;

    private String uniqueSequenceNumber = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getUniqueSequenceNumber() {
        return uniqueSequenceNumber;
    }

    public void setUniqueSequenceNumber(String uniqueSequenceNumber) {
        this.uniqueSequenceNumber = uniqueSequenceNumber;
    }

    public static MPResourceArray all(String paymentId) throws MPException {
        return all(paymentId, WITHOUT_CACHE);
    }

    public static MPResourceArray all(String paymentId, Boolean useCache) throws MPException {
        return all(paymentId, useCache, MPRequestOptions.createDefault());
    }

    @GET(path="/v1/payments/:payment_id/refunds")
    public static MPResourceArray all(String paymentId, Boolean useCache, MPRequestOptions requestOptions) throws MPException {
        return processMethodBulk(Refund.class, "all", useCache, requestOptions, paymentId);
    }

    public Refund save() throws MPException {
        return save(MPRequestOptions.createDefault());
    }

    @POST(path="/v1/payments/:payment_id/refunds")
    public Refund save(MPRequestOptions requestOptions) throws MPException {
        return processMethod("save", WITHOUT_CACHE, requestOptions);
    }

}
