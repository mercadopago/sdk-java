package com.mercadopago.resources;

import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.MPResourceArray;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.datastructures.advancedpayment.Source;

import java.util.*;

public class DisbursementRefund extends MPBase {

    private Integer id;
    private Integer paymentId;
    private Float amount;
    private Source source;
    private Date dateCreated;
    private String status;


    private transient Integer advancedPaymentId;
    private transient Long disbursementId;

    public Integer getAdvancedPaymentId() {
        return advancedPaymentId;
    }

    public DisbursementRefund setAdvancedPaymentId(Integer advancedPaymentId) {
        this.advancedPaymentId = advancedPaymentId;
        return this;
    }

    public Long getDisbursementId() {
        return disbursementId;
    }

    public DisbursementRefund setDisbursementId(Long disbursementId) {
        this.disbursementId = disbursementId;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public DisbursementRefund setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public DisbursementRefund setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
        return this;
    }

    public Float getAmount() {
        return amount;
    }

    public DisbursementRefund setAmount(Float amount) {
        this.amount = amount;
        return this;
    }

    public Source getSource() {
        return source;
    }

    public DisbursementRefund setSource(Source source) {
        this.source = source;
        return this;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public DisbursementRefund setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public DisbursementRefund setStatus(String status) {
        this.status = status;
        return this;
    }

    @GET(path="/v1/advanced_payments/:advanced_payment_id/refunds")
    public static MPResourceArray all(String advancedPaymentId, Boolean useCache, MPRequestOptions requestOptions) throws MPException {
        return processMethodBulk(Refund.class, "all", useCache, requestOptions, advancedPaymentId);
    }

    public DisbursementRefund save(float amount) throws MPException {
        if (amount > 0) {
            this.amount = amount;
        }

        return save(MPRequestOptions.createDefault());
    }

    @POST(path="/v1/advanced_payments/:advanced_payment_id/disbursements/:disbursement_id/refunds")
    public DisbursementRefund save(MPRequestOptions requestOptions) throws MPException {
        return processMethod("save", WITHOUT_CACHE, requestOptions);
    }

    @POST(path="/v1/advanced_payments/:advanced_payment_id/refunds")
    protected static MPResourceArray createAll(Long advancedPaymentId,MPRequestOptions requestOptions) throws MPException {

        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("advanced_payment_id", advancedPaymentId.toString());

        return processMethodBulk(DisbursementRefund.class, "createAll", pathParams, WITHOUT_CACHE, requestOptions);

    }

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
