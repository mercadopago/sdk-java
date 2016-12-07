package com.mercadopago.resources;

import com.google.gson.JsonObject;
import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPBaseResponse;
import com.mercadopago.core.annotations.idempotent.Idempotent;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.core.annotations.rest.PUT;
import com.mercadopago.core.annotations.validation.Size;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.datastructures.*;
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

    //Attributes
    private String id = null;
    private Date date_created = null;
    private Date date_approved = null;
    private Date date_last_updated = null;
    private Date money_release_date = null;
    private Integer collector_id = null;
    private OperationType operation_type = null;
    public enum OperationType {
        regular_payment,
        money_transfer,
        recurring_payment,
        account_fund,
        payment_addition,
        cellphone_recharge,
        pos_payment
    }
    private PayerPayment payer = null;
    private Boolean binary_mode = null;
    private Boolean live_mode = null;
    private Order order = null;
    private String external_reference = null;
    private String description = null;
    private JsonObject metadata = null;
    @Size(min=3, max=3) private CurrencyId currency_id = null;
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
    private Float transaction_amount = null;
    private Float transaction_amount_refunded = null;
    private Float coupon_amount = null;
    private Integer campaign_id = null;
    private String coupon_code = null;
    private TransactionDetails transaction_details = null;
    private ArrayList<FeeDetail> fee_detail = null;
    private Integer differential_pricing_id = null;
    private Float application_fee = null;
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
    private String status_detail = null;
    private Boolean capture = null;
    private Boolean captured = null;
    private String call_for_authorize_id = null;
    private String payment_method_id = null;
    private String issuer_id = null;
    private PaymentTypeId payment_type_id = null;
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
    private String statement_descriptor = null;
    private Integer installments = null;
    private String notification_url = null;
    private ArrayList<Refund> refunds = null;
    private AdditionalInfo additional_info = null;

    public String getId() {
        return id;
    }

    public Date getDate_created() {
        return date_created;
    }

    public Date getDate_approved() {
        return date_approved;
    }

    public Date getDate_last_updated() {
        return date_last_updated;
    }

    public Date getMoney_release_date() {
        return money_release_date;
    }

    public Integer getCollector_id() {
        return collector_id;
    }

    public OperationType getOperation_type() {
        return operation_type;
    }

    public Boolean getLive_mode() {
        return live_mode;
    }

    public CurrencyId getCurrency_id() {
        return currency_id;
    }

    public Float getTransaction_amount_refunded() {
        return transaction_amount_refunded;
    }

    public ArrayList<FeeDetail> getFee_detail() {
        return fee_detail;
    }

    public Status getStatus() {
        return status;
    }

    public String getStatus_detail() {
        return status_detail;
    }

    public Boolean getCaptured() {
        return captured;
    }

    public String getCall_for_authorize_id() {
        return call_for_authorize_id;
    }

    public PaymentTypeId getPayment_type_id() {
        return payment_type_id;
    }

    public Card getCard() {
        return card;
    }

    public ArrayList<Refund> getRefunds() {
        return refunds;
    }

    public void setCampaign_id(Integer campaign_id) {
        this.campaign_id = campaign_id;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public void setApplication_fee(Float application_fee) {
        this.application_fee = application_fee;
    }

    public void setCapture(Boolean capture) {
        this.capture = capture;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setAdditional_info(AdditionalInfo additional_info) {
        this.additional_info = additional_info;
    }

    public PayerPayment getPayer() {
        return payer;
    }

    public void setPayer(PayerPayment payer) {
        this.payer = payer;
    }

    public Boolean getBinary_mode() {
        return binary_mode;
    }

    public void setBinary_mode(Boolean binary_mode) {
        this.binary_mode = binary_mode;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getExternal_reference() {
        return external_reference;
    }

    public void setExternal_reference(String external_reference) {
        this.external_reference = external_reference;
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

    public Float getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(Float transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public Float getCoupon_amount() {
        return coupon_amount;
    }

    public void setCoupon_amount(Float coupon_amount) {
        this.coupon_amount = coupon_amount;
    }

    public Integer getDifferential_pricing_id() {
        return differential_pricing_id;
    }

    public void setDifferential_pricing_id(Integer differential_pricing_id) {
        this.differential_pricing_id = differential_pricing_id;
    }

    public String getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(String payment_method_id) {
        this.payment_method_id = payment_method_id;
    }

    public String getIssuer_id() {
        return issuer_id;
    }

    public void setIssuer_id(String issuer_id) {
        this.issuer_id = issuer_id;
    }

    public String getStatement_descriptor() {
        return statement_descriptor;
    }

    public void setStatement_descriptor(String statement_descriptor) {
        this.statement_descriptor = statement_descriptor;
    }

    public Integer getInstallments() {
        return installments;
    }

    public void setInstallments(Integer installments) {
        this.installments = installments;
    }

    public String getNotification_url() {
        return notification_url;
    }

    public void setNotification_url(String notification_url) {
        this.notification_url = notification_url;
    }

    //Methods
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
