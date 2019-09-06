package com.mercadopago.resources;

import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.core.annotations.rest.PUT;
import com.mercadopago.core.annotations.validation.Size;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.datastructures.merchantorder.Collector;
import com.mercadopago.resources.datastructures.merchantorder.Item;
import com.mercadopago.resources.datastructures.merchantorder.MerchantOrderPayment;
import com.mercadopago.resources.datastructures.merchantorder.Payer;
import com.mercadopago.resources.datastructures.merchantorder.Shipment;

import java.util.ArrayList;
import java.util.Date;

/**
 * Mercado Pago MercadoPago
 * This resource allows you to keep  the state of an order, grouping items, payments and shipments.
 *
 * Created by Eduardo Paoletta on 12/13/16.
 */
public class MerchantOrder extends MPBase {

    private String id = null;
    private String preferenceId = null;
    private Date dateCreated = null;
    private Date lastUpdate = null;
    private String applicationId = null;
    private String status = null;
    private String siteId = null;
    private Payer payer = null;
    private Collector collector = null;
    private Integer sponsorId = null;
    private ArrayList<MerchantOrderPayment> payments = null;
    private Float paidAmount = null;
    private Float refundedAmount = null;
    private Float shippingCost = null;
    private Boolean cancelled = null;
    private ArrayList<Item> items = null;
    private ArrayList<Shipment> shipments = null;
    @Size(max=500) private String notificationUrl = null;
    @Size(max=600) private String additionalInfo = null;
    @Size(max=256) private String externalReference = null;
    @Size(max=256) private String marketplace = null;
    private Float totalAmount = null;


    public String getId() {
        return id;
    }

    public String getPreferenceId() {
        return preferenceId;
    }

    public MerchantOrder setPreferenceId(String preferenceId) {
        this.preferenceId = preferenceId;
        return this;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public MerchantOrder setApplicationId(String applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public String getSiteId() {
        return siteId;
    }

    public MerchantOrder setSiteId(String siteId) {
        this.siteId = siteId;
        return this;
    }

    public Payer getPayer() {
        return payer;
    }

    public MerchantOrder setPayer(Payer payer) {
        this.payer = payer;
        return this;
    }

    public Collector getCollector() {
        return collector;
    }

    public MerchantOrder setCollector(Collector collector) {
        this.collector = collector;
        return this;
    }

    public Integer getSponsorId() {
        return sponsorId;
    }

    public MerchantOrder setSponsorId(Integer sponsorId) {
        this.sponsorId = sponsorId;
        return this;
    }

    public ArrayList<MerchantOrderPayment> getPayments() {
        return payments;
    }

    public Float getPaidAmount() {
        return paidAmount;
    }

    public Float getRefundedAmount() {
        return refundedAmount;
    }

    public Float getShippingCost() {
        return shippingCost;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public MerchantOrder setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
        return this;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public MerchantOrder setItems(ArrayList<Item> items) {
        this.items = items;
        return this;
    }

    public MerchantOrder appendItem(Item item) {
        if (items == null) {
            items = new ArrayList<Item>();
        }
        items.add(item);
        return this;
    }

    public ArrayList<Shipment> getShipments() {
        return shipments;
    }

    public MerchantOrder setShipments(ArrayList<Shipment> shipments) {
        this.shipments = shipments;
        return this;
    }

    public MerchantOrder appendShipment(Shipment shipment) {
        if (shipments == null) {
            shipments = new ArrayList<Shipment>();
        }
        shipments.add(shipment);
        return this;
    }

    public String getNotificationUrl() {
        return notificationUrl;
    }

    public MerchantOrder setNotificationUrl(String notificationUrl) {
        this.notificationUrl = notificationUrl;
        return this;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public MerchantOrder setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public MerchantOrder setExternalReference(String externalReference) {
        this.externalReference = externalReference;
        return this;
    }

    public String getMarketplace() {
        return marketplace;
    }

    public MerchantOrder setMarketplace(String marketplace) {
        this.marketplace = marketplace;
        return this;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }


    public static MerchantOrder findById(String id) throws MPException {
        return findById(id, WITHOUT_CACHE);
    }

    public static MerchantOrder findById(String id, Boolean useCache) throws MPException {
        return findById(id, useCache, MPRequestOptions.createDefault());
    }

    @GET(path="/merchant_orders/:id")
    public static MerchantOrder findById(String id, Boolean useCache, MPRequestOptions requestOptions) throws MPException {
        return processMethod(MerchantOrder.class, "findById", useCache, requestOptions, id);
    }

    public MerchantOrder save() throws MPException {
        return save(MPRequestOptions.createDefault());
    }

    @POST(path="/merchant_orders")
    public MerchantOrder save(MPRequestOptions requestOptions) throws MPException {
        return super.processMethod("save", WITHOUT_CACHE, requestOptions);
    }

    public MerchantOrder update() throws MPException {
        return update(MPRequestOptions.createDefault());
    }

    @PUT(path="/merchant_orders/:id")
    public MerchantOrder update(MPRequestOptions requestOptions) throws MPException {
        return super.processMethod("update", WITHOUT_CACHE, requestOptions);
    }

}
