package com.mercadopago.resources;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mercadopago.core.MPBase;
import com.mercadopago.core.annotations.idempotent.Idempotent;
import com.mercadopago.resources.datastructures.advancedpayment.AdditionalInfo;
import com.mercadopago.resources.datastructures.advancedpayment.Disbursement;
import com.mercadopago.resources.datastructures.advancedpayment.Payer;
import com.mercadopago.resources.datastructures.advancedpayment.Payment;

import java.util.ArrayList;
import java.util.Date;

@Idempotent
public class AdvancedPayment extends MPBase {

    private Integer id;
    private String status;
    private String externalReference;
    private String description;
    private Date dateCreated;
    private Date dateLastUpdated;
    private Payer payer;
    private ArrayList<Payment> payments;
    private ArrayList<Disbursement> disbursements;
    private boolean binaryMode;
    private JsonObject metadata = null;
    private String applicationId;
    private AdditionalInfo additionalInfo;


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


}
