package com.mercadopago.resources;

import com.google.gson.JsonObject;
import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.MPResourceArray;
import com.mercadopago.core.annotations.idempotent.Idempotent;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.core.annotations.rest.PUT;
import com.mercadopago.core.annotations.validation.Numeric;
import com.mercadopago.core.annotations.validation.Size;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.datastructures.payment.PointOfInteraction;
import com.mercadopago.resources.datastructures.payment.TransactionDetails;
import com.mercadopago.resources.datastructures.payment.AdditionalInfo;
import com.mercadopago.resources.datastructures.payment.FeeDetail;
import com.mercadopago.resources.datastructures.payment.Item;
import com.mercadopago.resources.datastructures.payment.Order;
import com.mercadopago.resources.datastructures.payment.Payer;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * This class provides the methods to access the API that will allow you to create your own payment experience on your website.
 * From basic to advanced configurations, you control the whole experience.
 */
@Idempotent
public class Payment extends MPBase {

    private String id = null;
    private Date dateCreated = null;
    private Date dateApproved = null;
    private Date dateLastUpdated = null;
    private Date moneyReleaseDate = null;
    private Integer collectorId = null;
    private String authorizationCode = null;
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
        UYU,
        USD
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
        prepaid_card,
        digital_currency,
        digital_wallet
    }
    private String token = null;
    private Card card = null;
    private String statementDescriptor = null;
    @Numeric(min=1, fractionDigits=0) private Integer installments = null;
    private String notificationUrl = null;
    private ArrayList<Refund> refunds = null;
    private AdditionalInfo additionalInfo = null;
    private String callbackUrl = null;
    private Integer sponsorId = null;
    private String processingMode = null;
    private String merchantAccountId = null;
    private String paymentMethodOptionId = null;
    private Date dateOfExpiration = null;
    private PointOfInteraction pointOfInteraction = null;

    /**
     * @return payment ID
     */
    public String getId() {
        return id;
    }

    /**
     * @return date of creation
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @return approved date
     */
    public Date getDateApproved() {
        return dateApproved;
    }

    /**
     * @return date of last update
     */
    public Date getDateLastUpdated() {
        return dateLastUpdated;
    }

    /**
     * @return money release date
     */
    public Date getMoneyReleaseDate() {
        return moneyReleaseDate;
    }

    /**
     * @return collector ID
     */
    public Integer getCollectorId() {
        return collectorId;
    }

    /**
     * @return authorization code
     */
    public String getAuthorizationCode() {
        return authorizationCode;
    }

    /**
     * @return operation type
     */
    public OperationType getOperationType() {
        return operationType;
    }

    /**
     * @return payer information
     */
    public Payer getPayer() {
        return payer;
    }

    /**
     * @param payer payer information
     * @return the payment
     */
    public Payment setPayer(Payer payer) {
        this.payer = payer;
        return this;
    }

    /**
     * @return true if uses binary mode, otherwise false
     */
    public Boolean getBinaryMode() {
        return binaryMode;
    }

    /**
     * @param binaryMode true if uses binary mode, otherwise false
     * @return the payment
     */
    public Payment setBinaryMode(Boolean binaryMode) {
        this.binaryMode = binaryMode;
        return this;
    }

    /**
     * @return true if uses live mode, otherwise false
     */
    public Boolean getLiveMode() {
        return liveMode;
    }

    /**
     * @return order information
     */
    public Order getOrder() {
        return order;
    }

    /**
     * @param order order information
     * @return the payment
     */
    public Payment setOrder(Order order) {
        this.order = order;
        return this;
    }

    /**
     * @return identification in seller system
     */
    public String getExternalReference() {
        return externalReference;
    }

    /**
     * @param externalReference identification in seller system
     * @return the payment
     */
    public Payment setExternalReference(String externalReference) {
        this.externalReference = externalReference;
        return this;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description description
     * @return the payment
     */
    public Payment setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * @return metadata information
     */
    public JsonObject getMetadata() {
        return metadata;
    }

    /**
     * @param metadata metadata information
     * @return the payment
     */
    public Payment setMetadata(JsonObject metadata) {
        this.metadata = metadata;
        return this;
    }

    /**
     * @return currency ID
     */
    public CurrencyId getCurrencyId() {
        return currencyId;
    }

    /**
     * @return transaction amount
     */
    public Float getTransactionAmount() {
        return transactionAmount;
    }

    /**
     * @param transactionAmount transaction amount
     * @return the payment
     */
    public Payment setTransactionAmount(Float transactionAmount) {
        this.transactionAmount = transactionAmount;
        return this;
    }

    /**
     * @return transaction amount refunded
     */
    public Float getTransactionAmountRefunded() {
        return transactionAmountRefunded;
    }

    /**
     * @return coupon amount
     */
    public Float getCouponAmount() {
        return couponAmount;
    }

    /**
     * @param couponAmount coupon amount
     * @return the payment
     */
    public Payment setCouponAmount(Float couponAmount) {
        this.couponAmount = couponAmount;
        return this;
    }

    /**
     * @param campaignId campaign ID
     * @return the payment
     */
    public Payment setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
        return this;
    }

    /**
     * @param couponCode coupon code
     * @return the payment
     */
    public Payment setCouponCode(String couponCode) {
        this.couponCode = couponCode;
        return this;
    }

    /**
     * @return transaction details
     */
    public TransactionDetails getTransactionDetails() {
        return transactionDetails;
    }

    /**
     * @param transactionDetails transaction details
     * @return the payment
     */
    public Payment setTransactionDetails(TransactionDetails transactionDetails) {
        this.transactionDetails = transactionDetails;
        return this;
    }

    /**
     * @return fee details
     */
    public ArrayList<FeeDetail> getFeeDetails() {
        return feeDetails;
    }

    /**
     * @return differential pricing ID
     */
    public Integer getDifferentialPricingId() {
        return differentialPricingId;
    }

    /**
     * @param differentialPricingId differential pricing ID
     * @return the payment
     */
    public Payment setDifferentialPricingId(Integer differentialPricingId) {
        this.differentialPricingId = differentialPricingId;
        return this;
    }

    /**
     * @param applicationFee application fee
     * @return the payment
     */
    public Payment setApplicationFee(Float applicationFee) {
        this.applicationFee = applicationFee;
        return this;
    }

    /**
     * @return payment status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status payment status
     * @return the payment
     */
    public Payment setStatus(Status status) {
        this.status = status;
        return this;
    }

    /**
     * @return status detail
     */
    public String getStatusDetail() {
        return statusDetail;
    }

    /**
     * @param capture true if capture payment, otherwise false
     * @return
     */
    public Payment setCapture(Boolean capture) {
        this.capture = capture;
        return this;
    }

    /**
     * @return true if the payment was captured, otherwise false
     */
    public Boolean getCaptured() {
        return captured;
    }

    /**
     * @return call for authorize ID
     */
    public String getCallForAuthorizeId() {
        return callForAuthorizeId;
    }

    /**
     * @return payment method ID
     */
    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    /**
     * @param paymentMethodId payment method ID
     * @return the payment
     */
    public Payment setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
        return this;
    }

    /**
     * @return issuer ID
     */
    public String getIssuerId() {
        return issuerId;
    }

    /**
     * @param issuerId issuer ID
     * @return the payment
     */
    public Payment setIssuerId(String issuerId) {
        this.issuerId = issuerId;
        return this;
    }

    /**
     * @return payment type ID
     */
    public PaymentTypeId getPaymentTypeId() {
        return paymentTypeId;
    }

    /**
     * @param token card token
     * @return the payment
     */
    public Payment setToken(String token) {
        this.token = token;
        return this;
    }

    /**
     * @return card information
     */
    public Card getCard() {
        return card;
    }

    /**
     * @return how will look the payment in the card bill
     */
    public String getStatementDescriptor() {
        return statementDescriptor;
    }

    /**
     * @param statementDescriptor how will look the payment in the card bill
     * @return the payment
     */
    public Payment setStatementDescriptor(String statementDescriptor) {
        this.statementDescriptor = statementDescriptor;
        return this;
    }

    /**
     * @return number of installments
     */
    public Integer getInstallments() {
        return installments;
    }

    /**
     * @param installments number of installments
     * @return the payment
     */
    public Payment setInstallments(Integer installments) {
        this.installments = installments;
        return this;
    }

    /**
     * @return notification URL
     */
    public String getNotificationUrl() {
        return notificationUrl;
    }

    /**
     * @param notificationUrl notification URL
     * @return the payment
     */
    public Payment setNotificationUrl(String notificationUrl) {
        this.notificationUrl = notificationUrl;
        return this;
    }

    /**
     * @return payment refunds
     */
    public ArrayList<Refund> getRefunds() {
        return refunds;
    }

    /**
     * @param additionalInfo additional info
     * @return
     */
    public Payment setAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }

    /**
     * @return callback URL
     */
    public String getCallbackUrl() {
        return callbackUrl;
    }

    /**
     * @param callbackUrl callback URL
     * @return the payment
     */
    public Payment setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
        return this;
    }

    /**
     * Searches the payments
     * @see <a href="https://www.mercadopago.com/developers/en/reference/payments/_payments_search/get/">api docs</a>
     * @param filters search filters
     * @param useCache true if will use cache, otherwise false
     * @return the searched payments
     * @throws MPException an error if the request fails
     */
    public static MPResourceArray search(HashMap<String, String> filters, Boolean useCache) throws MPException {
        return search(filters, useCache, MPRequestOptions.createDefault());
    }

    /**
     * @return sponsor ID
     */
    public Integer getSponsorId() {
        return this.sponsorId;
    }

    /**
     * @param sponsorId sponsor ID
     * @return the payment
     */
    public Payment setSponsorId(Integer sponsorId) {
        this.sponsorId = sponsorId;
        return this;
    }

    /**
     * @return processing mode
     */
    public String getProcessingMode() {
        return this.processingMode;
    }

    /**
     * @param processingMode processing mode
     * @return the payment
     */
    public Payment setProcessingMode(String processingMode){
        this.processingMode = processingMode;
        return this;
    }

    /**
     * @param processingMode processing mode
     * @return the payment
     */
    public Payment setPaymentMode(String processingMode) {
        this.processingMode = processingMode;
        return this;
    }

    /**
     * @return merchant account ID
     */
    public String getMerchantAccountid() {
        return merchantAccountId;
    }

    /**
     * @param merchantAccountId merchant account ID
     * @return the payment
     */
    public Payment setMerchantAccountId(String merchantAccountId) {
        this.merchantAccountId = merchantAccountId;
        return this;
    }

    /**
     * @return payment method option ID
     */
    public String getPaymentMethodOptionId() {
        return paymentMethodOptionId;
    }

    /**
     * @param paymentMethodOptionId payment method option ID
     * @return the payment
     */
    public Payment setPaymentMethodOptionId(String paymentMethodOptionId) {
        this.paymentMethodOptionId = paymentMethodOptionId;
        return this;
    }

    /**
     * @return date of expiration
     */
    public Date getDateOfExpiration() {
        return dateOfExpiration;
    }

    /**
     * @param dateOfExpiration payment date of expiration
     * @return the payment
     */
    public Payment setDateOfExpiration(Date dateOfExpiration) {
        this.dateOfExpiration = dateOfExpiration;
        return this;
    }

    /**
     * @return point of interaction
     */
    public PointOfInteraction getPointOfInteraction() {
        return pointOfInteraction;
    }

    /**
     * @param pointOfInteraction point of interaction
     * @return the payment
     */
    public Payment setPointOfInteraction(PointOfInteraction pointOfInteraction) {
        this.pointOfInteraction = pointOfInteraction;
        return this;
    }

    /**
     * Searches the payments
     * @see <a href="https://www.mercadopago.com/developers/en/reference/payments/_payments_search/get/">api docs</a>
     * @param filters search filters
     * @param useCache true if will use cache, otherwise false
     * @param requestOptions request options
     * @return the searched payments
     * @throws MPException an error if the request fails
     */
    @GET(path="/v1/payments/search")
    public static MPResourceArray search(HashMap<String, String> filters, Boolean useCache, MPRequestOptions requestOptions) throws MPException {
        return processMethodBulk(Payment.class, "search", filters, useCache, requestOptions);
    }

    /**
     * Finds the payment by your ID
     * @param id payment ID
     * @return the payment
     * @throws MPException an error if the request fails
     */
    public static Payment findById(String id) throws MPException {
        return findById(id, WITHOUT_CACHE);
    }

    /**
     * Finds the payment by your ID
     * @see <a href="https://www.mercadopago.com/developers/en/reference/payments/_payments_id/get/">api docs</a>
     * @param id payment ID
     * @param useCache true if will use cache, otherwise false
     * @return the payment
     * @throws MPException an error if the request fails
     */
    public static Payment findById(String id, Boolean useCache) throws MPException {
        return findById(id, useCache, MPRequestOptions.createDefault());
    }

    /**
     * Finds the payment by your ID
     * @see <a href="https://www.mercadopago.com/developers/en/reference/payments/_payments_id/get/">api docs</a>
     * @param id payment ID
     * @param useCache true if will use cache, otherwise false
     * @param requestOptions request options
     * @return the payment
     * @throws MPException an error if the request fails
     */
    @GET(path="/v1/payments/:id")
    public static Payment findById(String id, Boolean useCache, MPRequestOptions requestOptions) throws MPException {
        return processMethod(Payment.class, "findById", useCache, requestOptions, id);
    }

    /**
     * Saves a new payment
     * @see <a href="https://www.mercadopago.com/developers/en/reference/payments/_payments/post/">api docs</a>
     * @return the saved payment
     * @throws MPException an error if the request fails
     */
    public Payment save() throws MPException {
        return save(MPRequestOptions.createDefault());
    }

    /**
     * Saves a new payment
     * @see <a href="https://www.mercadopago.com/developers/en/reference/payments/_payments/post/">api docs</a>
     * @param requestOptions request options
     * @return the saved payment
     * @throws MPException an error if the request fails
     */
    @POST(path="/v1/payments")
    public Payment save(MPRequestOptions requestOptions) throws MPException {
        if (requestOptions == null) {
            requestOptions = MPRequestOptions.createDefault();
        }
        addTrackingHeaders(requestOptions);
        return processMethod("save", WITHOUT_CACHE, requestOptions);
    }

    /**
     * Updates the payment editable properties
     * @return the updated payment
     * @throws MPException an error if the request fails
     */
    public Payment update() throws MPException {
        return update(MPRequestOptions.createDefault());
    }

    /**
     * Updates the payment editable properties
     * @param requestOptions request options
     * @return the updated payment
     * @throws MPException an error if the request fails
     */
    @PUT(path="/v1/payments/:id")
    public Payment update(MPRequestOptions requestOptions) throws MPException {
        return processMethod("update", WITHOUT_CACHE, requestOptions);
    }

    /**
     * Refunds a payment
     * @return the refunded payment
     * @throws MPException an error if the request fails
     */
    public Payment refund() throws MPException {
        return refund(MPRequestOptions.createDefault());
    }

    /**
     * Refunds a payment
     * @param requestOptions request options
     * @return the refunded payment
     * @throws MPException an error if the request fails
     */
    public Payment refund(MPRequestOptions requestOptions) throws MPException {
        return refund(null, requestOptions);
    }

    /**
     * Refunds a payment
     * @param amount amount to be refunded
     * @return the refunded payment
     * @throws MPException an error if the request fails
     */
    public Payment refund(Float amount) throws MPException {
        return refund(amount, MPRequestOptions.createDefault());
    }

    /**
     * Refunds a payment
     * @param amount amount to be refunded
     * @param requestOptions request options
     * @return the refunded payment
     * @throws MPException an error if the request fails
     */
    public Payment refund(Float amount, MPRequestOptions requestOptions) throws MPException {
        // Create a refund
        Refund refund = new Refund();
        refund.setPaymentId(this.getId());
        refund.setAmount(amount);
        refund.save(requestOptions);
        this.lastApiResponse = refund.getLastApiResponse();
        // If refund has been successfully created then update the instance values

        if (refund.getId() != null) {
            Payment payment = Payment.findById(this.getId(), WITHOUT_CACHE, requestOptions); // Get updated payment instance
            this.status = payment.getStatus();
            this.statusDetail = payment.getStatusDetail();
            this.refunds = payment.getRefunds();
            this.transactionAmountRefunded = payment.getTransactionAmountRefunded();
        }
        return this;
    }
}
