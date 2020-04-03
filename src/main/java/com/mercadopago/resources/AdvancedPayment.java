package com.mercadopago.resources;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mercadopago.core.MPBase;
import com.mercadopago.core.annotations.idempotent.Idempotent;
import com.mercadopago.resources.datastructures.advancedpayment.Disbursement;
import com.mercadopago.resources.datastructures.advancedpayment.Payment;

import java.util.ArrayList;

@Idempotent
public class AdvancedPayment extends MPBase {

    private Payment payer;
    private ArrayList<Payment> payments;
    private ArrayList<Disbursement> disbursements;
    private boolean binaryMode;
    private JsonObject metadata = null;
    private String id = null;
    private String applicationId;
    private String externalReference;
    private String description;
    private boolean capture;

    public String getId() {
        return id;
    }

    public AdvancedPayment setId(String id) {
        this.id = id;
        return this;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public AdvancedPayment setApplicationId(String applicationId) {
        this.applicationId = applicationId;
        return this;
    }

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

    public Payment getPayer() {
        return payer;
    }

    public AdvancedPayment setPayer(Payment payer) {
        this.payer = payer;
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

    public boolean isBinaryMode() {
        return binaryMode;
    }

    public AdvancedPayment setBinaryMode(boolean binaryMode) {
        this.binaryMode = binaryMode;
        return this;
    }

    public boolean isCapture() {
        return capture;
    }

    public AdvancedPayment setCapture(boolean capture) {
        this.capture = capture;
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
}
