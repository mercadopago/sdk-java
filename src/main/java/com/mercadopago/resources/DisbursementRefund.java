package com.mercadopago.resources;

import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.MPResourceArray;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.datastructures.advancedpayment.Source;

import java.util.*;

/**
 * Disbursement refund data
 */
public class DisbursementRefund extends MPBase {

    private Integer id;
    private Integer paymentId;
    private Float amount;
    private Source source;
    private Date dateCreated;
    private String status;


    private transient Integer advancedPaymentId;
    private transient Long disbursementId;

    /**
     * @return advanced payment ID
     */
    public Integer getAdvancedPaymentId() {
        return advancedPaymentId;
    }

    /**
     * @param advancedPaymentId advanced payment ID
     * @return the disbursement refund
     */
    public DisbursementRefund setAdvancedPaymentId(Integer advancedPaymentId) {
        this.advancedPaymentId = advancedPaymentId;
        return this;
    }

    /**
     * @return disbursement ID
     */
    public Long getDisbursementId() {
        return disbursementId;
    }

    /**
     * @param disbursementId disbursement ID
     * @return the disbursement refund
     */
    public DisbursementRefund setDisbursementId(Long disbursementId) {
        this.disbursementId = disbursementId;
        return this;
    }

    /**
     * @return disbursement refund ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id disbursement refund ID
     * @return the disbursement refund
     */
    public DisbursementRefund setId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * @return payment ID
     */
    public Integer getPaymentId() {
        return paymentId;
    }

    /**
     * @param paymentId payment ID
     * @return the disbursement refund
     */
    public DisbursementRefund setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
        return this;
    }

    /**
     * @return amount
     */
    public Float getAmount() {
        return amount;
    }

    /**
     * @param amount amount
     * @return the disbursement refund
     */
    public DisbursementRefund setAmount(Float amount) {
        this.amount = amount;
        return this;
    }

    /**
     * @return source
     */
    public Source getSource() {
        return source;
    }

    /**
     * @param source source
     * @return the disbursement refund
     */
    public DisbursementRefund setSource(Source source) {
        this.source = source;
        return this;
    }

    /**
     * @return date of creation
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated date of creation
     * @return the disbursement refund
     */
    public DisbursementRefund setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status status
     * @return the disbursement refund
     */
    public DisbursementRefund setStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * Get all disbursements refunds
     * @param advancedPaymentId advanced payment ID
     * @param useCache true if will use cache, otherwise false
     * @return the disbursements refunds
     * @throws MPException an error if the request fails
     */
    public static MPResourceArray all(String advancedPaymentId, Boolean useCache) throws MPException {
        return all(advancedPaymentId, useCache, MPRequestOptions.createDefault());
    }

    /**
     * Get all disbursements refunds
     * @param advancedPaymentId advanced payment ID
     * @param useCache true if will use cache, otherwise false
     * @param requestOptions request options
     * @return the disbursements refunds
     * @throws MPException an error if the request fails
     */
    @GET(path="/v1/advanced_payments/:advanced_payment_id/refunds")
    public static MPResourceArray all(String advancedPaymentId, Boolean useCache, MPRequestOptions requestOptions) throws MPException {
        return processMethodBulk(Refund.class, "all", useCache, requestOptions, advancedPaymentId);
    }

    /**
     * Saves a new disbursement refund
     * @param amount amount
     * @return the saved disbursement refund
     * @throws MPException an error if the request fails
     */
    public DisbursementRefund save(float amount) throws MPException {
        return save(amount, MPRequestOptions.createDefault());
    }

    /**
     * Saves a new disbursement refund
     * @param amount amount
     * @param requestOptions request options
     * @return the saved disbursement refund
     * @throws MPException an error if the request fails
     */
    public DisbursementRefund save(float amount, MPRequestOptions requestOptions) throws MPException {
        if (amount > 0) {
            this.amount = amount;
        }

        return save(requestOptions);
    }

    /**
     * Saves a new disbursement refund
     * @return the saved disbursement refund
     * @throws MPException an error if the request fails
     */
    public DisbursementRefund save() throws MPException {
        return save(MPRequestOptions.createDefault());
    }

    /**
     * Saves a new disbursement refund
     * @param requestOptions request options
     * @return the saved disbursement refund
     * @throws MPException an error if the request fails
     */
    @POST(path="/v1/advanced_payments/:advanced_payment_id/disbursements/:disbursement_id/refunds")
    public DisbursementRefund save(MPRequestOptions requestOptions) throws MPException {
        return processMethod("save", WITHOUT_CACHE, requestOptions);
    }

    /**
     * Creates refunds to all disbursements
     * @param advancedPaymentId advanced payment ID
     * @param requestOptions request options
     * @return all disbursements refunds
     * @throws MPException an error if the request fails
     */
    @POST(path="/v1/advanced_payments/:advanced_payment_id/refunds")
    protected static MPResourceArray createAll(Long advancedPaymentId,MPRequestOptions requestOptions) throws MPException {

        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("advanced_payment_id", advancedPaymentId.toString());

        return processMethodBulk(DisbursementRefund.class, "createAll", pathParams, WITHOUT_CACHE, requestOptions);

    }

    /**
     * Creates a disbursement refund
     * @param advancedPaymentId advanced payment ID
     * @param disbursementId disbursement ID
     * @param amount amount
     * @param requestOptions request options
     * @return the created disbursement refund
     * @throws MPException an error if the request fails
     */
    @POST(path="/v1/advanced_payments/:advanced_payment_id/disbursements/:disbursement_id/refunds")
    protected static DisbursementRefund create(Long advancedPaymentId, Long disbursementId, float amount, MPRequestOptions requestOptions) throws MPException {
        Map<String,String> pathParams = new HashMap<String, String>();
        pathParams.put("advanced_payment_id", advancedPaymentId.toString());
        pathParams.put("disbursement_id", disbursementId.toString());

        DisbursementRefund refund = new DisbursementRefund()
                .setAmount(amount);

        return processMethod(DisbursementRefund.class, refund, "create", pathParams,WITHOUT_CACHE, requestOptions);
    }

}
