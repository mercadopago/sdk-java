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
import com.mercadopago.resources.datastructures.advancedpayment.Disbursement;
import com.mercadopago.resources.datastructures.advancedpayment.Payer;
import com.mercadopago.resources.datastructures.advancedpayment.Payment;
import com.mercadopago.resources.datastructures.advancedpayment.Refund;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Idempotent
public class AdvancedPayment extends MPBase {

    private Integer id = null;
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

    public ArrayList<Payment> getPayments() {
        return payments;
    }

    public AdvancedPayment setPayments(ArrayList<Payment> payments) {
        this.payments = payments;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateLastUpdated() {
        return dateLastUpdated;
    }

    public void setDateLastUpdated(Date dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }


    @POST(path="/v1/advanced_payments")
    public AdvancedPayment save(AdvancedPayment advancedPayment) throws MPException {
        if(advancedPayment.getMetadata() == null){
            advancedPayment.setMetadata(new JsonObject());
        }
        return processMethod(AdvancedPayment.class, advancedPayment, "save", null, WITHOUT_CACHE, MPRequestOptions.createDefault());
    }

    @PUT(path="/v1/advanced_payments/:id")
    public AdvancedPayment update() throws MPException {
        return processMethod("update", WITHOUT_CACHE,MPRequestOptions.createDefault() );
    }

    @GET(path="/v1/advanced_payments/:id")
    public static AdvancedPayment findById( String id) throws MPException {
        return processMethod(AdvancedPayment.class, "findById", WITHOUT_CACHE, MPRequestOptions.createDefault(), id);
    }

//    @POST(path="/v1/advanced_payments/:id/refunds")
//    public ArrayList<Refund> saveReembolso() throws MPException {
//        ArrayList<Refund> listRefund = new ArrayList<Refund>();
//        Refund ad =  processMethod( "saveReembolso", WITHOUT_CACHE, MPRequestOptions.createDefault());
//        listRefund.add(ad);
//        return listRefund;
//    }

    @POST(path="/v1/advanced_payments/:id/refunds")
    public static MPResourceArray all() throws MPException {
        return  processMethodBulk(Refund.class, "all", WITHOUT_CACHE, MPRequestOptions.createDefault());
    }


    @POST(path="/v1/advanced_payments/:id/refunds")
    public ArrayList<Refund> saveReembolso() throws MPException {
        ArrayList<Refund> listRefund = new ArrayList<Refund>();
        Refund ad =  processMethod( "saveReembolso", WITHOUT_CACHE, MPRequestOptions.createDefault());
        listRefund.add(ad);
        return listRefund;
    }

    @POST(path="/v1/advanced_payments/:id/disbursements/:disbursementId/refunds")
    public List<AdvancedPayment> saveReembolsoParcial() throws MPException {
        return processMethod( "saveReembolsoParcial", WITHOUT_CACHE, MPRequestOptions.createDefault());
    }

    @POST(path="/v1/advanced_payments/:id/disburses")
    public AdvancedPayment saveMudancaDataLancamento() throws MPException {
        return processMethod( "saveMudancaDataLancamento", WITHOUT_CACHE, MPRequestOptions.createDefault());
    }

    @POST(path="/v1/advanced_payments/:id/disbursements/:disbursementId/disburses")
    public AdvancedPayment saveAlteracaoDataLancamento() throws MPException {
        return processMethod( "saveAlteracaoDataLancamento", WITHOUT_CACHE, MPRequestOptions.createDefault());
    }


}
