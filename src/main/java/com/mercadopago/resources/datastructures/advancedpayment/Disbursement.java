package com.mercadopago.resources.datastructures.advancedpayment;

public class Disbursement {
    private Integer id;
    private Float amount;
    private String externalReference;
    private String collectorId;
    private Float applicationFee;
    private Float moneyReleaseDays;
    private AdditionalInfo additionalInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public String getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(String collectorId) {
        this.collectorId = collectorId;
    }

    public Float getApplicationFee() {
        return applicationFee;
    }

    public void setApplicationFee(Float applicationFee) {
        this.applicationFee = applicationFee;
    }

    public Float getMoneyReleaseDays() {
        return moneyReleaseDays;
    }

    public void setMoneyReleaseDays(Float moneyReleaseDays) {
        this.moneyReleaseDays = moneyReleaseDays;
    }

    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
