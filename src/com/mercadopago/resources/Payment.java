package com.mercadopago.resources;

import com.google.gson.JsonObject;
import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPBaseResponse;
import com.mercadopago.core.annotations.idempotent.Idempotent;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.core.annotations.rest.PUT;
import com.mercadopago.core.annotations.validation.Numeric;
import com.mercadopago.core.annotations.validation.Size;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.datastructures.payment.*;
import com.mercadopago.resources.interfaces.IPNRecoverable;

import java.util.ArrayList;
import java.util.Date;

/**
 * Mercado Pago SDK
 * This resource allows you to create, modify or read payments
 *
 * Created by Eduardo Paoletta on 12/2/16.
 */
@Idempotent
public class Payment extends MPBase implements IPNRecoverable {

    private String id = null;
    private Date dateCreated = null;
    private Date dateApproved = null;
    private Date dateLastUpdated = null;
    private Date moneyReleaseDate = null;
    private Integer collectorId = null;
    private OperationType operationType = null;
    public enum OperationType {
        regular_payment,
        money_transfer,
        recurring_payment,
        account_fund,
        payment_addition,
        cellphone_recharge,
        pos_payment
    }
    private Payer payer = null;
    private Boolean binaryMode = null;
    private Boolean liveMode = null;
    private Order order = null;
    private String externalReference = null;
    private String description = null;
    private JsonObject metadata = null;
    @Size(min=3, max=3) private CurrencyId currencyId = null;
    public enum CurrencyId {
        ARS,
        BRL,
        VEF,
        CLP,
        MXN,
        COP,
        PEN,
        UYU
    }
    private Float transactionAmount = null;
    private Float transactionAmountRefunded = null;
    private Float couponAmount = null;
    private Integer campaignId = null;
    private String couponCode = null;
    private TransactionDetails transactionDetails = null;
    private ArrayList<FeeDetail> feeDetails = null;
    private Integer differentialPricingId = null;
    private Float applicationFee = null;
    private Status status = null;
    public enum Status {
        pending,
        approved,
        authorized,
        in_process,
        in_mediation,
        rejected,
        cancelled,
        refunded,
        charged_back
    }
    private String statusDetail = null;
    private Boolean capture = null;
    private Boolean captured = null;
    private String callForAuthorizeId = null;
    private String paymentMethodId = null;
    private String issuerId = null;
    private PaymentTypeId paymentTypeId = null;
    public enum PaymentTypeId {
        account_money,
        ticket,
        bank_transfer,
        atm,
        credit_card,
        debit_card,
        prepaid_card
    }
    private String token = null;
    private Card card = null;
    private String statementDescriptor = null;
    @Numeric(min=1, fractionDigits=0) private Integer installments = null;
    private String notificationUrl = null;
    private ArrayList<Refund> refunds = null;
    private AdditionalInfo additionalInfo = null;


    public String getId() {
        return id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getDateApproved() {
        return dateApproved;
    }

    public Date getDateLastUpdated() {
        return dateLastUpdated;
    }

    public Date getMoneyReleaseDate() {
        return moneyReleaseDate;
    }

    public Integer getCollectorId() {
        return collectorId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public Payer getPayer() {
        return payer;
    }

    public void setPayer(Payer payer) {
        this.payer = payer;
    }

    public Boolean getBinaryMode() {
        return binaryMode;
    }

    public void setBinaryMode(Boolean binaryMode) {
        this.binaryMode = binaryMode;
    }

    public Boolean getLiveMode() {
        return liveMode;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    public JsonObject getMetadata() {
        return metadata;
    }

    public void setMetadata(JsonObject metadata) {
        this.metadata = metadata;
    }

    public CurrencyId getCurrencyId() {
        return currencyId;
    }

    public Float getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Float transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Float getTransactionAmountRefunded() {
        return transactionAmountRefunded;
    }

    public Float getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Float couponAmount) {
        this.couponAmount = couponAmount;
    }

    public void setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public TransactionDetails getTransactionDetails() {
        return transactionDetails;
    }

    public ArrayList<FeeDetail> getFeeDetails() {
        return feeDetails;
    }

    public Integer getDifferentialPricingId() {
        return differentialPricingId;
    }

    public void setDifferentialPricingId(Integer differentialPricingId) {
        this.differentialPricingId = differentialPricingId;
    }

    public void setApplicationFee(Float applicationFee) {
        this.applicationFee = applicationFee;
    }

    public Status getStatus() {
        return status;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public void setCapture(Boolean capture) {
        this.capture = capture;
    }

    public Boolean getCaptured() {
        return captured;
    }

    public String getCallForAuthorizeId() {
        return callForAuthorizeId;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    public PaymentTypeId getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Card getCard() {
        return card;
    }

    public String getStatementDescriptor() {
        return statementDescriptor;
    }

    public void setStatementDescriptor(String statementDescriptor) {
        this.statementDescriptor = statementDescriptor;
    }

    public Integer getInstallments() {
        return installments;
    }

    public void setInstallments(Integer installments) {
        this.installments = installments;
    }


    public String getNotificationUrl() {
        return notificationUrl;
    }

    public void setNotificationUrl(String notificationUrl) {
        this.notificationUrl = notificationUrl;
    }

    public ArrayList<Refund> getRefunds() {
        return refunds;
    }

    public void setAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }


    @GET(path="/v1/payments/:id")
    public MPBaseResponse load(String id) throws MPException {
        return super.processMethod("load", id);
    }

    @POST(path="/v1/payments")
    public MPBaseResponse create() throws MPException {
        return super.processMethod("create");
    }

    @PUT(path="/v1/payments/:id")
    public MPBaseResponse update(String id) throws MPException {
        return super.processMethod("update", id);
    }

}
