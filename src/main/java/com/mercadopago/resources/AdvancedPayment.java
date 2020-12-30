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

/**
 * Provides a solution for Marketplace payments where the business model requires splitting money between multiple sellers
 */
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

    /**
     * @return true if the payment was captured, otherwise false
     */
    public Boolean getCapture() {
        return capture;
    }

    /**
     * @param capture capture true or not false the advanced payment
     * @return the advanced payment
     */
    public AdvancedPayment setCapture(Boolean capture) {
        this.capture = capture;
        return this;
    }

    /**
     * @return money release date
     */
    public Date getMoneyReleaseDate() {
        return moneyReleaseDate;
    }

    /**
     * @param moneyReleaseDate money release date
     * @return the advanced payment
     */
    public AdvancedPayment setMoneyReleaseDate(Date moneyReleaseDate) {
        this.moneyReleaseDate = moneyReleaseDate;
        return this;
    }

    /**
     * @return true if the payment was captured, otherwise false
     */
    public boolean isCapture() {
        return capture;
    }

    /**
     * @return payments made by the payer 
     */
    public ArrayList<Payment> getPayments() {
        return payments;
    }

    /**
     * @param payments payments made by the payer
     * @return the advanced payment
     */
    public AdvancedPayment setPayments(ArrayList<Payment> payments) {
        this.payments = payments;
        return this;
    }

    /**
     * Add a payment into the advanced payment
     * @param payment payments made by the payer
     * @return the advanced payment
     */
    public AdvancedPayment addPayment(Payment payment) {
        if (this.payments == null) {
            this.payments = new ArrayList<Payment>();
        }
        this.payments.add(payment);
        return this;
    }

    /**
     * @param disbursements payments that correspond to each seller
     * @return the advanced payment
     */
    public AdvancedPayment setDisbursements(ArrayList<Disbursement> disbursements) {
        this.disbursements = disbursements;
        return this;
    }

    /**
     * @return payments that correspond to each seller
     */
    public ArrayList<Disbursement> getDisbursements() {
        return disbursements;
    }

    /**
     * Add a disbursement into the advanced payment
     * @param disbursement payments that correspond to each seller
     * @return the advanced payment
     */
    public AdvancedPayment addDisbursement(Disbursement disbursement) {
        if (this.disbursements == null) {
            this.disbursements = new ArrayList<Disbursement>();
        }
        this.disbursements.add(disbursement);
        return this;
    }

    /**
     * @return payer information
     */
    public Payer getPayer() {
        return payer;
    }

    /**
     * @param payer payer information
     * @return the advanced payment
     */
    public AdvancedPayment setPayer(Payer payer) {
        this.payer = payer;
        return this;
    }

    /**
     * @return true if uses binary mode, otherwise false
     */
    public boolean isBinaryMode() {
        return binaryMode;
    }

    /**
     * @return metadata
     */
    public JsonObject getMetadata() {
        return metadata;
    }

    /**
     * Add a metadata value
     * @param key metadata key
     * @param value metadata value
     * @return the advanced payment
     */
    public AdvancedPayment addMetadata(String key, String value) {
        if (this.metadata == null){
            metadata = new JsonObject();
        }

        this.metadata.addProperty(key, value);
        return this;
    }

    /**
     * @param metadata metadata
     * @return the advanced payment
     */
    public AdvancedPayment setMetadata(JsonObject metadata) {
        this.metadata = metadata;
        return this;
    }

    /**
     * @return advanced payment ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id advanced payment ID
     * @return the advanced payment
     */
    public AdvancedPayment setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * @return advanced payment status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status advanced payment status
     * @return the advanced payment
     */
    public AdvancedPayment setStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * @return ID given by the merchant in their system
     */
    public String getExternalReference() {
        return externalReference;
    }

    /**
     * @param externalReference ID given by the merchant in their system
     * @return the advanced payment
     */
    public AdvancedPayment setExternalReference(String externalReference) {
        this.externalReference = externalReference;
        return this;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description description
     * @return the advanced payment
     */
    public AdvancedPayment setDescription(String description) {
        this.description = description;
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
     * @return the advanced payment
     */
    public AdvancedPayment setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    /**
     * @return date of last update
     */
    public Date getDateLastUpdated() {
        return dateLastUpdated;
    }

    /**
     * @param dateLastUpdated date of last update
     * @return the advanced payment
     */
    public AdvancedPayment setDateLastUpdated(Date dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
        return this;
    }

    /**
     * @return true if uses binary mode, otherwise false
     */
    public Boolean getBinaryMode() {
        return binaryMode;
    }

    /**
     * @param binaryMode true if uses binary mode, otherwise false
     * @return the advanced payment
     */
    public AdvancedPayment setBinaryMode(Boolean binaryMode) {
        this.binaryMode = binaryMode;
        return this;
    }

    /**
     * @return application ID
     */
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * @param applicationId application ID
     * @return the advanced payment
     */
    public AdvancedPayment setApplicationId(String applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    /**
     * @return additional info
     */
    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * @param additionalInfo additional info
     * @return the advanced payment
     */
    public AdvancedPayment setAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }

    /**
     * Saves a new advanced payment
     * @see <a href="https://www.mercadopago.com/developers/en/reference/advanced_payments/_advanced_payments/post/">api docs</a>
     * @return the saved advanced payment
     * @throws MPException an error if the request fails
     */
    public AdvancedPayment save() throws MPException {
        return save(MPRequestOptions.createDefault());
    }

    /**
     * Saves a new advanced payment
     * @see <a href="https://www.mercadopago.com/developers/en/reference/advanced_payments/_advanced_payments/post/">api docs</a>
     * @param requestOptions request options
     * @return the saved advanced payment
     * @throws MPException an error if the request fails
     */
    @POST(path="/v1/advanced_payments")
    public AdvancedPayment save(MPRequestOptions requestOptions) throws MPException {
        if(this.getMetadata() == null){
            this.setMetadata(new JsonObject());
        }
        return processMethod(AdvancedPayment.class, this, "save", null, WITHOUT_CACHE, requestOptions);
    }

    /**
     * Captures a advanced payment
     * @param id the advanced payment ID
     * @return true if the advanced payment was captured, otherwise false
     * @throws MPException an error if the request fails
     */
    public static boolean capture(Long id) throws MPException {
        return capture(id, MPRequestOptions.createDefault());
    }

    /**
     * Captures a advanced payment
     * @param id the advanced payment ID
     * @param requestOptions request options
     * @return true if the advanced payment was captured, otherwise false
     * @throws MPException an error if the request fails
     */
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

    /**
     * Updates a advanced payment
     * @return the advanced payment
     * @throws MPException an error if the request fails
     */
    public AdvancedPayment update() throws MPException{
        return update(MPRequestOptions.createDefault());
    }

    /**
     * Updates a advanced payment
     * @param requestOptions request options
     * @return the advanced payment
     * @throws MPException an error if the request fails
     */
    @PUT(path="/v1/advanced_payments/:id")
    public AdvancedPayment update(MPRequestOptions requestOptions) throws MPException {
        return processMethod("update", WITHOUT_CACHE, requestOptions);
    }

    /**
     * Find a advanced payment by ID
     * @see <a href="https://www.mercadopago.com/developers/en/reference/advanced_payments/_advanced_payments_id/get/">api docs</a>
     * @param id advanced payment ID
     * @return the advanced payment
     * @throws MPException an error if the request fails
     */
    public static AdvancedPayment findById(String id) throws MPException {
        return findById(id, MPRequestOptions.createDefault());
    }

    /**
     * Find a advanced payment by ID
     * @see <a href="https://www.mercadopago.com/developers/en/reference/advanced_payments/_advanced_payments_id/get/">api docs</a>
     * @param id advanced payment ID
     * @param requestOptions request options
     * @return the advanced payment
     * @throws MPException an error if the request fails
     */
    @GET(path="/v1/advanced_payments/:id")
    public static AdvancedPayment findById(String id, MPRequestOptions requestOptions) throws MPException {
        return processMethod(AdvancedPayment.class, "findById", WITHOUT_CACHE, requestOptions, id);
    }

    /**
     * Cancels a advanced payment
     * @param id the advanced payment ID
     * @return true if the advanced payment was cancelled, otherwise false
     * @throws MPException an error if the request fails
     */
    public static boolean cancel(long id) throws MPException {
        return cancel(id, MPRequestOptions.createDefault());
    }

    /**
     * Cancels a advanced payment
     * @param id the advanced payment ID
     * @param requestOptions request options
     * @return true if the advanced payment was cancelled, otherwise false
     * @throws MPException an error if the request fails
     */
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

    /**
     * Updates the money release date of the advanced payment
     * @param advancedPaymentId the advanced payment ID
     * @param releaseDate the release date
     * @return true if the money release date was updated, otherwise false
     * @throws MPException an error if the request fails
     */
    public static boolean updateReleaseDate(Long advancedPaymentId, Date releaseDate) throws MPException {
        return updateReleaseDate(advancedPaymentId, releaseDate, MPRequestOptions.createDefault());
    }

    /**
     * Updates the money release date of the advanced payment
     * @param advancedPaymentId the advanced payment ID
     * @param releaseDate the release date
     * @param requestOptions request options
     * @return true if the money release date was updated, otherwise false
     * @throws MPException an error if the request fails
     */
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

    /**
     * Refunds a disbursement
     * @param advancedPaymentId advanced payment ID
     * @param disbursementId disbursement ID
     * @param amount amount to be refunded
     * @param requestOptions request options
     * @return the disbursement refunded
     * @throws MPException an error if the request fails
     */
    public static DisbursementRefund refund(Long advancedPaymentId, Long disbursementId, float amount, MPRequestOptions requestOptions) throws MPException {
        return DisbursementRefund.create(advancedPaymentId, disbursementId,amount, requestOptions);
    }

    /**
     * Refunds a disbursement
     * @param advancedPaymentId advanced payment ID
     * @param disbursementId disbursement ID
     * @param amount amount to be refunded
     * @return the disbursement refunded
     * @throws MPException an error if the request fails
     */
    public static DisbursementRefund refund(Long advancedPaymentId, Long disbursementId, float amount) throws MPException {
        return DisbursementRefund.create(advancedPaymentId, disbursementId,amount, MPRequestOptions.createDefault());
    }

    /**
     * Refunds all the disbursements
     * @param advancedPaymentId advanced payment ID
     * @return a list of disbursements refunded
     * @throws MPException an error if the request fails
     */
    public static MPResourceArray refundAll(long advancedPaymentId) throws MPException {
        return DisbursementRefund.createAll(advancedPaymentId, MPRequestOptions.createDefault());
    }

    /**
     * Refunds all the disbursements
     * @param advancedPaymentId advanced payment ID
     * @param requestOptions request options
     * @return a list of disbursements refunded
     * @throws MPException an error if the request fails
     */
    @POST(path="/v1/advanced_payments/:advanced_payment_id/refunds")
    public static MPResourceArray refundAll(long advancedPaymentId, MPRequestOptions requestOptions) throws MPException {
        return DisbursementRefund.createAll(advancedPaymentId, requestOptions);
    }
}
