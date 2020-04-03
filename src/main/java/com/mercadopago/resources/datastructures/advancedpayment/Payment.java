package com.mercadopago.resources.datastructures.advancedpayment;

import java.util.Date;

public class Payment {
    private String paymentMethodId;
    private String paymentTypeId;
    private Date dateOfExpiration;
    private Float transactionAmount;
    private String processingMode;
    private String description;
    private String externalReference;

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public Payment setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
        return this;
    }

    public String getPaymentTypeId() {
        return paymentTypeId;
    }

    public Payment setPaymentTypeId(String paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
        return this;
    }

    public Date getDateOfExpiration() {
        return dateOfExpiration;
    }

    public Payment setDateOfExpiration(Date dateOfExpiration) {
        this.dateOfExpiration = dateOfExpiration;
        return this;
    }

    public Float getTransactionAmount() {
        return transactionAmount;
    }

    public Payment setTransactionAmount(Float transactionAmount) {
        this.transactionAmount = transactionAmount;
        return this;
    }

    public String getProcessingMode() {
        return processingMode;
    }

    public Payment setProcessingMode(String processingMode) {
        this.processingMode = processingMode;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Payment setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public Payment setExternalReference(String externalReference) {
        this.externalReference = externalReference;
        return this;
    }
}
