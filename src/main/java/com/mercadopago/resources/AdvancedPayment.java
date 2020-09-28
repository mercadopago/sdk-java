package com.mercadopago.resources;

import com.google.gson.JsonObject;
import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.MPResourceArray;
import com.mercadopago.core.annotations.idempotent.Idempotent;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.core.annotations.rest.PUT;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.datastructures.advancedpayment.AdditionalInfo;
import com.mercadopago.resources.datastructures.advancedpayment.Payer;
import com.mercadopago.resources.datastructures.advancedpayment.Payment;

import java.util.*;

@Idempotent
public class AdvancedPayment extends MPBase {

    private Long id = null;
    private String status = null;
    private String externalReference = null;
    private String description = null;
    private Date dateCreated = null;
    private Date dateLastUpdated = null;
    private Payer payer = null;
    private ArrayList<Payment> payments = null;
    private ArrayList<Disbursement> disbursements = null;
    private Boolean binaryMode = null;
    private JsonObject metadata = null;
    private String applicationId = null;
    private AdditionalInfo additionalInfo = null;
    private Boolean capture = null;
    private Date moneyReleaseDate = null;

    public Boolean getCapture() {
        return capture;
    }

    public AdvancedPayment setCapture(Boolean capture) {
        this.capture = capture;
        return this;
    }

    public Date getMoneyReleaseDate() {
        return moneyReleaseDate;
    }

    public AdvancedPayment setMoneyReleaseDate(Date moneyReleaseDate) {
        this.moneyReleaseDate = moneyReleaseDate;
        return this;
    }

    public boolean isCapture() {
        return capture;
    }

    public AdvancedPayment setCapture(boolean capture) {
        this.capture = capture;
        return this;
    }

    public ArrayList<Payment> getPayments() {
        return payments;
    }

    public AdvancedPayment setPayments(ArrayList<Payment> payments) {
        this.payments = payments;
        return this;
    }

    public AdvancedPayment addPayment(Payment payment) {
        if (this.payments == null) {
            this.payments = new ArrayList<Payment>();
        }
        this.payments.add(payment);
        return this;
    }

    public AdvancedPayment setDisbursements(ArrayList<Disbursement> disbursements) {
        this.disbursements = disbursements;
        return this;
    }

    public ArrayList<Disbursement> getDisbursements() {
        return disbursements;
    }

    public AdvancedPayment addDisbursement(Disbursement disbursement) {
        if (this.disbursements == null) {
            this.disbursements = new ArrayList<Disbursement>();
        }
        this.disbursements.add(disbursement);
        return this;
    }

    public Payer getPayer() {
        return payer;
    }

    public AdvancedPayment setPayer(Payer payer) {
        this.payer = payer;
        return this;
    }

    public boolean isBinaryMode() {
        return binaryMode;
    }

    public AdvancedPayment setBinaryMode(boolean binaryMode) {
        this.binaryMode = binaryMode;
        return this;
    }


    public JsonObject getMetadata() {
        return metadata;
    }

    public AdvancedPayment addMetadata(String key, String value) {
        if (this.metadata == null){
            metadata = new JsonObject();
        }

        this.metadata.addProperty(key, String.valueOf(value));
        return this;
    }

    public AdvancedPayment setMetadata(JsonObject metadata) {
        this.metadata = metadata;
        return this;
    }

    public Long getId() {
        return id;
    }

    public AdvancedPayment setId(Long id) {
        this.id = id;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public AdvancedPayment setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public AdvancedPayment setExternalReference(String externalReference) {
        this.externalReference = externalReference;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AdvancedPayment setDescription(String description) {
        this.description = description;
        return this;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public AdvancedPayment setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public Date getDateLastUpdated() {
        return dateLastUpdated;
    }

    public AdvancedPayment setDateLastUpdated(Date dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
        return this;
    }

    public Boolean getBinaryMode() {
        return binaryMode;
    }

    public AdvancedPayment setBinaryMode(Boolean binaryMode) {
        this.binaryMode = binaryMode;
        return this;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public AdvancedPayment setApplicationId(String applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public AdvancedPayment setAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }

    public AdvancedPayment save() throws MPException {
        return save(MPRequestOptions.createDefault());
    }

    @POST(path="/v1/advanced_payments")
    public AdvancedPayment save(MPRequestOptions requestOptions) throws MPException {
        if(this.getMetadata() == null){
            this.setMetadata(new JsonObject());
        }
        return processMethod(AdvancedPayment.class, this, "save", null, WITHOUT_CACHE, requestOptions);
    }

    public static boolean capture(Long id) throws MPException {
        return capture(id, MPRequestOptions.createDefault());
    }

    @PUT(path="/v1/advanced_payments/:id")
    public static boolean capture(Long id, MPRequestOptions requestOptions) throws MPException {
        AdvancedPayment advancedPayment = new AdvancedPayment()
                .setCapture(true);

        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("id", id.toString());

        AdvancedPayment response = processMethod(AdvancedPayment.class, advancedPayment, "update", queryParams, WITHOUT_CACHE, requestOptions);
        if(response.getLastApiResponse().getStatusCode() >= 200 && response.getLastApiResponse().getStatusCode() < 300){
            return true;
        }
        return false;
    }

    public AdvancedPayment update() throws MPException{
        return update(MPRequestOptions.createDefault());
    }

    @PUT(path="/v1/advanced_payments/:id")
    public AdvancedPayment update(MPRequestOptions requestOptions) throws MPException {
        return processMethod("update", WITHOUT_CACHE, requestOptions);
    }

    public static AdvancedPayment findById(String id) throws MPException {
        return findById(id, MPRequestOptions.createDefault());
    }

    @GET(path="/v1/advanced_payments/:id")
    public static AdvancedPayment findById(String id, MPRequestOptions requestOptions) throws MPException {
        return processMethod(AdvancedPayment.class, "findById", WITHOUT_CACHE, requestOptions, id);
    }

    public static boolean cancel(long id) throws MPException {
        return cancel(id, MPRequestOptions.createDefault());
    }

    @PUT(path="/v1/advanced_payments/:id")
    public static boolean cancel(Long id, MPRequestOptions requestOptions) throws MPException {
        AdvancedPayment advancedPayment = new AdvancedPayment()
                .setStatus("cancelled");

        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("id", id.toString());

        AdvancedPayment response = processMethod(AdvancedPayment.class, advancedPayment, "update", queryParams, WITHOUT_CACHE, requestOptions);
        if (response.getLastApiResponse().getStatusCode() >= 200 && response.getLastApiResponse().getStatusCode() < 300){
            return true;
        }

        return false;
    }

    public static boolean updateReleaseDate(Long advancedPaymentId, Date releaseDate) throws MPException {
        return updateReleaseDate(advancedPaymentId, releaseDate, MPRequestOptions.createDefault());
    }

    @POST(path="/v1/advanced_payments/:id/disburses")
    public static boolean updateReleaseDate(Long advancedPaymentId, Date releaseDate, MPRequestOptions requestOptions) throws MPException {
        AdvancedPayment advancedPayment = new AdvancedPayment()
                .setMoneyReleaseDate(releaseDate);

        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("id", advancedPaymentId.toString());

        AdvancedPayment response = processMethod(AdvancedPayment.class, advancedPayment, "updateReleaseDate", queryParams, WITHOUT_CACHE, requestOptions);
        if (response.getLastApiResponse().getStatusCode() >= 200 && response.getLastApiResponse().getStatusCode() < 300){
            return true;
        }

        return false;
    }

    public static DisbursementRefund refund(Long advancedPaymentId, Long disbursementId, float amount, MPRequestOptions requestOptions) throws MPException {
        return DisbursementRefund.create(advancedPaymentId, disbursementId,amount, requestOptions);
    }

    public static DisbursementRefund refund(Long advancedPaymentId, Long disbursementId, float amount) throws MPException {
        return DisbursementRefund.create(advancedPaymentId, disbursementId,amount, MPRequestOptions.createDefault());
    }

    public static MPResourceArray refundAll(long advancedPaymentId) throws MPException {
        return DisbursementRefund.createAll(advancedPaymentId, MPRequestOptions.createDefault());
    }

    @POST(path="/v1/advanced_payments/:advanced_payment_id/refunds")
    public static MPResourceArray refundAll(long advancedPaymentId, MPRequestOptions requestOptions) throws MPException {
        return DisbursementRefund.createAll(advancedPaymentId, requestOptions);
    }
}
