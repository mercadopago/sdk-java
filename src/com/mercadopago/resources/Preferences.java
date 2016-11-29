package com.mercadopago.resources;

import com.mercadopago.core.MPBase;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.core.annotations.rest.PUT;
import com.mercadopago.core.annotations.validation.NotNull;
import com.mercadopago.core.annotations.validation.Numeric;
import com.mercadopago.core.annotations.validation.Size;
import com.mercadopago.resources.datastructures.*;
import com.mercadopago.exceptions.MPException;


import java.util.ArrayList;
import java.util.Date;

/**
 * Mercado Pago SDK
 * This resource allows you to set up, during the payment process, all the item information, any accepted means of payment and many other options.
 *
 * Created by Eduardo Paoletta on 11/9/16.
 */
public class Preferences extends MPBase {

    //Attributes
    @NotNull private ArrayList<Item> items = null;
    @NotNull private Payer payer = null;
    private PaymentMethods payment_methods = null;
    private Shipments shipments = null;
    private BackUrls back_urls = null;
    @Size(max=500) private String notification_url = null;
    private String id = null;
    private String init_point = null;
    private String sandbox_init_point = null;
    private Date date_created = null;
    private OperationType operation_type = null;
    public enum OperationType {
        regular_payment,
        money_transfer
    }
    @Size(max=600) private String additional_info = null;
    private AutoReturn auto_return = null;
    public enum AutoReturn {
        approved,
        all
    }
    @Size(max=256) private String external_reference = null;
    private Boolean expires = null;
    private Date expiration_date_from = null;
    private Date expiration_date_to = null;
    private Integer collector_id = null;
    private Integer client_id = null;
    @Size(max=256) private String marketplace = null;
    @Numeric(min=.01f) private Float marketplace_fee = null;
    private DifferentialPricing differential_pricing = null;

    //Getters/Setters
    public ArrayList<Item> getItems() {
        return items;
    }

    public Preferences setItems(ArrayList<Item> items) {
        this.items = items;
        return this;
    }

    public ArrayList<Item> appendItem(Item item) {
        if (items == null)
            items = new ArrayList<Item>();
        items.add(item);
        return getItems();
    }

    public Payer getPayer() {
        return payer;
    }

    public Preferences setPayer(Payer payer) {
        this.payer = payer;
        return this;
    }

    public PaymentMethods getPaymentMethods() {
        return payment_methods;
    }

    public Preferences setPaymentMethods(PaymentMethods paymentMethods) {
        this.payment_methods = paymentMethods;
        return this;
    }

    public Shipments getShipments() {
        return shipments;
    }

    public Preferences setShipments(Shipments shipments) {
        this.shipments = shipments;
        return this;
    }

    public BackUrls getBackUrls() {
        return back_urls;
    }

    public Preferences setBackUrls(BackUrls backUrls) {
        this.back_urls = backUrls;
        return this;
    }

    public String getNotificationUrl() {
        return notification_url;
    }

    public Preferences setNotificationUrl(String notificationUrl) {
        this.notification_url = notificationUrl;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getInitPoint() {
        return init_point;
    }

    public String getSandboxInitPoint() {
        return sandbox_init_point;
    }

    public Date getDateCreated() {
        return date_created;
    }

    public OperationType getOperationType() {
        return operation_type;
    }

    public String getAdditionalInfo() {
        return additional_info;
    }

    public Preferences setAdditionalInfo(String additionalInfo) {
        this.additional_info = additionalInfo;
        return this;
    }

    public AutoReturn getAutoReturn() {
        return auto_return;
    }

    public Preferences setAutoReturn(AutoReturn autoReturn) {
        this.auto_return = autoReturn;
        return this;
    }

    public String getExternalReference() {
        return external_reference;
    }

    public Preferences setExternalReference(String externalReference) {
        this.external_reference = externalReference;
        return this;
    }

    public Boolean getExpires() {
        return expires;
    }

    public Preferences setExpires(Boolean expires) {
        this.expires = expires;
        return this;
    }

    public Date getExpirationDateFrom() {
        return expiration_date_from;
    }

    public Preferences setExpirationDateFrom(Date expirationDateFrom) {
        this.expiration_date_from = expirationDateFrom;
        return this;
    }

    public Date getExpirationDateTo() {
        return expiration_date_to;
    }

    public Preferences setExpirationDateTo(Date expirationDateTo) {
        this.expiration_date_to = expirationDateTo;
        return this;
    }

    public Integer getCollectorId() {
        return collector_id;
    }

    public Preferences setCollectorId(Integer collectorId) {
        this.collector_id = collectorId;
        return this;
    }

    public Integer getClientId() {
        return client_id;
    }

    public Preferences setClientId(Integer clientId) {
        this.client_id = clientId;
        return this;
    }

    public String getMarketplace() {
        return marketplace;
    }

    public Preferences setMarketplace(String marketplace) {
        this.marketplace = marketplace;
        return this;
    }

    public Float getMarketplaceFee() {
        return marketplace_fee;
    }

    public Preferences setMarketplaceFee(Float marketplaceFee) {
        this.marketplace_fee = marketplaceFee;
        return this;
    }

    public DifferentialPricing getDifferentialPricing() {
        return differential_pricing;
    }

    public Preferences setDifferentialPricing(DifferentialPricing differentialPricing) {
        this.differential_pricing = differentialPricing;
        return this;
    }

    //Methods
    @GET(path="/checkout/preferences/:param")
    public String load(String id) throws MPException {
        return super.processMethod("load", id);
    }

    @POST(path="/checkout/preferences")
    public String create() throws MPException {
        return super.processMethod("create");
    }

    @PUT(path="/checkout/preferences/:param")
    public String update(String id) throws MPException {
        return super.processMethod("update", id);
    }

}
