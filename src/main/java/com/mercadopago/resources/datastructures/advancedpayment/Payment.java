package com.mercadopago.resources.datastructures.advancedpayment;



import java.util.Date;

public class Payment {
    private Integer id;
    private String status;
    private String statusDetail;
    private String paymentTypeId;
    private String paymentMethodId;
    private String token;
    private Float transactionAmount;
    private Integer installments;
    private String processingMode;
    private String issuerId;
    private String description;
    private Boolean capture;
    private String externalReference;
    private String statementDescriptor;
    private String dateOfExpiration;
    private TransactionDetails transactionDetails;

    public Integer getId() {
        return id;
    }

    public Payment setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Payment setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public Payment setStatusDetail(String statusDetail) {
        this.statusDetail = statusDetail;
        return this;
    }

    public String getPaymentTypeId() {
        return paymentTypeId;
    }

    public Payment setPaymentTypeId(String paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
        return this;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public Payment setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
        return this;
    }

    public String getToken() {
        return token;
    }

    public Payment setToken(String token) {
        this.token = token;
        return this;
    }

    public Float getTransactionAmount() {
        return transactionAmount;
    }

    public Payment setTransactionAmount(Float transactionAmount) {
        this.transactionAmount = transactionAmount;
        return this;
    }

    public Integer getInstallments() {
        return installments;
    }

    public Payment setInstallments(Integer installments) {
        this.installments = installments;
        return this;
    }

    public String getProcessingMode() {
        return processingMode;
    }

    public Payment setProcessingMode(String processingMode) {
        this.processingMode = processingMode;
        return this;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public Payment setIssuerId(String issuerId) {
        this.issuerId = issuerId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Payment setDescription(String description) {
        this.description = description;
        return this;
    }

    public Boolean getCapture() {
        return capture;
    }

    public Payment setCapture(Boolean capture) {
        this.capture = capture;
        return this;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public Payment setExternalReference(String externalReference) {
        this.externalReference = externalReference;
        return this;
    }

    public String getStatementDescriptor() {
        return statementDescriptor;
    }

    public Payment setStatementDescriptor(String statementDescriptor) {
        this.statementDescriptor = statementDescriptor;
        return this;
    }

    public String getDateOfExpiration() {
        return dateOfExpiration;
    }

    public Payment setDateOfExpiration(String dateOfExpiration) {
        this.dateOfExpiration = dateOfExpiration;
        return this;
    }

    public TransactionDetails getTransactionDetails() {
        return transactionDetails;
    }

    public Payment setTransactionDetails(TransactionDetails transactionDetails) {
        this.transactionDetails = transactionDetails;
        return this;
    }
}
