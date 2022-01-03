package com.mercadopago.resources;

import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.MPResourceArray;
import com.mercadopago.core.annotations.idempotent.Idempotent;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.datastructures.payment.Source;

import java.util.Date;

/**
 * This class will allow you to refund payments created through the Payments class.
 * You can refund a payment within 180 days after it was approved.
 * You must have sufficient funds in your account in order to successfully refund the payment amount. Otherwise, you will get a 400 Bad Request error.
 */
@Idempotent
public class Refund extends MPBase {

    private String id = null;

    private transient String paymentId = null;

    private Float amount = null;

    private Source source = null;

    private Date dateCreated = null;

    private String uniqueSequenceNumber = null;

    /**
     * @return refund ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id refund ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return payment ID
     */
    public String getPaymentId() {
        return paymentId;
    }

    /**
     * @param paymentId payment ID
     */
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * @return refund amount
     */
    public Float getAmount() {
        return amount;
    }

    /**
     * @param amount refund amount
     */
    public void setAmount(Float amount) {
        this.amount = amount;
    }

    /**
     * @return source
     */
    public Source getSource() {
        return source;
    }

    /**
     * @param source source
     */
    public void setSource(Source source) {
        this.source = source;
    }

    /**
     * @return date of creation
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated date of creation
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * @return unique sequence number
     */
    public String getUniqueSequenceNumber() {
        return uniqueSequenceNumber;
    }

    /**
     * @param uniqueSequenceNumber unique sequence number
     */
    public void setUniqueSequenceNumber(String uniqueSequenceNumber) {
        this.uniqueSequenceNumber = uniqueSequenceNumber;
    }

    /**
     * Get all refunds of a payment
     * @param paymentId payment ID
     * @return the refunds of the payment
     * @throws MPException an error if the request fails
     */
    public static MPResourceArray all(String paymentId) throws MPException {
        return all(paymentId, WITHOUT_CACHE);
    }

    /**
     * Get all refunds of a payment
     * @param paymentId payment ID
     * @param useCache true if will use cache, otherwise false
     * @return the refunds of the payment
     * @throws MPException an error if the request fails
     */
    public static MPResourceArray all(String paymentId, Boolean useCache) throws MPException {
        return all(paymentId, useCache, MPRequestOptions.createDefault());
    }

    /**
     * Get all refunds of a payment
     * @param paymentId payment ID
     * @param useCache true if will use cache, otherwise false
     * @param requestOptions request options
     * @return the refunds of the payment
     * @throws MPException an error if the request fails
     */
    @GET(path="/v1/payments/:payment_id/refunds")
    public static MPResourceArray all(String paymentId, Boolean useCache, MPRequestOptions requestOptions) throws MPException {
        return processMethodBulk(Refund.class, "all", useCache, requestOptions, paymentId);
    }

    /**
     * Saves a new refund
     * @return the saved refund
     * @throws MPException an error if the request fails
     */
    public Refund save() throws MPException {
        return save(MPRequestOptions.createDefault());
    }

    /**
     * Saves a new refund
     * @param requestOptions request options
     * @return the saved refund
     * @throws MPException an error if the request fails
     */
    @POST(path="/v1/payments/:payment_id/refunds")
    public Refund save(MPRequestOptions requestOptions) throws MPException {
        return processMethod("save", WITHOUT_CACHE, requestOptions);
    }

}
