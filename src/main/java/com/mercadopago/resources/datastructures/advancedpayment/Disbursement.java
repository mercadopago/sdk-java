package com.mercadopago.resources.datastructures.advancedpayment;

public class Disbursement {
    private Float amount;
    private String externalReference;
    private String collectorId;
    private Float applicationFee;
    private AdditionalInfo additionalInfo = null;

    public Float getAmount() {
        return amount;
    }

    public Disbursement setAmount(Float amount) {
        this.amount = amount;
        return this;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public Disbursement setExternalReference(String externalReference) {
        this.externalReference = externalReference;
        return this;
    }

    public String getCollectorId() {
        return collectorId;
    }

    public Disbursement setCollectorId(String collectorId) {
        this.collectorId = collectorId;
        return this;
    }

    public Float getApplicationFee() {
        return applicationFee;
    }

    public Disbursement setApplicationFee(Float applicationFee) {
        this.applicationFee = applicationFee;
        return this;
    }

    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public Disbursement setAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }
}
