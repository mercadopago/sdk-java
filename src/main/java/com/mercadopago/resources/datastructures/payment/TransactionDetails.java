package com.mercadopago.resources.datastructures.payment;

/**
 * Mercado Pago SDK
 * Transaction Details class
 *
 * Created by Eduardo Paoletta on 12/2/16.
 */
public class TransactionDetails {

    private String financialInstitution = null;
    private Float netReceivedAmount = null;
    private Float totalPaidAmount = null;
    private Float installmentAmount = null;
    private Float overpaidAmount = null;
    private String externalResourceUrl = null;
    private String paymentMethodReferenceId = null;


    public String getFinancialInstitution() {
        return financialInstitution;
    }

    public Float getNetReceivedAmount() {
        return netReceivedAmount;
    }

    public Float getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public Float getInstallmentAmount() {
        return installmentAmount;
    }

    public Float getOverpaidAmount() {
        return overpaidAmount;
    }

    public String getExternalResourceUrl() {
        return externalResourceUrl;
    }

    public String getPaymentMethodReferenceId() {
        return paymentMethodReferenceId;
    }

    public TransactionDetails setFinancialInstitution(String financialInstitution) {
        this.financialInstitution = financialInstitution;
        return this;
    }

    public TransactionDetails setNetReceivedAmount(Float netReceivedAmount) {
        this.netReceivedAmount = netReceivedAmount;
        return this;
    }

    public TransactionDetails setTotalPaidAmount(Float totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
        return this;
    }

    public TransactionDetails setInstallmentAmount(Float installmentAmount) {
        this.installmentAmount = installmentAmount;
        return this;
    }

    public TransactionDetails setOverpaidAmount(Float overpaidAmount) {
        this.overpaidAmount = overpaidAmount;
        return this;
    }

    public TransactionDetails setExternalResourceUrl(String externalResourceUrl) {
        this.externalResourceUrl = externalResourceUrl;
        return this;
    }

    public TransactionDetails setPaymentMethodReferenceId(String paymentMethodReferenceId) {
        this.paymentMethodReferenceId = paymentMethodReferenceId;
        return this;
    }
}
