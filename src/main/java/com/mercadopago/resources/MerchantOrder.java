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
 * This class will allow you to create and manage your orders. You can attach one or more payments in your merchant order.
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

    /**
     * @return merchant order ID
     */
    public String getId() {
        return id;
    }

    /**
     * @return preference ID
     */
    public String getPreferenceId() {
        return preferenceId;
    }

    /**
     * @param preferenceId preference ID
     * @return the merchant order
     */
    public MerchantOrder setPreferenceId(String preferenceId) {
        this.preferenceId = preferenceId;
        return this;
    }

    /**
     * @return date of creation
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @return date of last update
     */
    public Date getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @return application ID
     */
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * @param applicationId application ID
     * @return the merchant order
     */
    public MerchantOrder setApplicationId(String applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return site ID
     */
    public String getSiteId() {
        return siteId;
    }

    /**
     * @param siteId site ID
     * @return the merchant order
     */
    public MerchantOrder setSiteId(String siteId) {
        this.siteId = siteId;
        return this;
    }

    /**
     * @return payer information
     */
    public Payer getPayer() {
        return payer;
    }

    /**
     * @param payer payer information
     * @return the merchant order
     */
    public MerchantOrder setPayer(Payer payer) {
        this.payer = payer;
        return this;
    }

    /**
     * @return collector information
     */
    public Collector getCollector() {
        return collector;
    }

    /**
     * @param collector collector information
     * @return the merchant order
     */
    public MerchantOrder setCollector(Collector collector) {
        this.collector = collector;
        return this;
    }

    /**
     * @return sponsor ID
     */
    public Integer getSponsorId() {
        return sponsorId;
    }

    /**
     * @param sponsorId sponsor ID
     * @return the merchant order
     */
    public MerchantOrder setSponsorId(Integer sponsorId) {
        this.sponsorId = sponsorId;
        return this;
    }

    /**
     * @return list of payments
     */
    public ArrayList<MerchantOrderPayment> getPayments() {
        return payments;
    }

    /**
     * @return paid amount
     */
    public Float getPaidAmount() {
        return paidAmount;
    }

    /**
     * @return refunded amount
     */
    public Float getRefundedAmount() {
        return refundedAmount;
    }

    /**
     * @return shipping cost
     */
    public Float getShippingCost() {
        return shippingCost;
    }

    /**
     * @return true if the order was cancelled, otherwise false
     */
    public Boolean getCancelled() {
        return cancelled;
    }

    /**
     * @param cancelled true if the order was cancelled, otherwise false
     * @return the merchant order
     */
    public MerchantOrder setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
        return this;
    }

    /**
     * @return list of items
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * @param items list of items
     * @return the merchant order
     */
    public MerchantOrder setItems(ArrayList<Item> items) {
        this.items = items;
        return this;
    }

    /**
     * @param item item to add
     * @return the merchant order
     */
    public MerchantOrder appendItem(Item item) {
        if (items == null) {
            items = new ArrayList<Item>();
        }
        items.add(item);
        return this;
    }

    /**
     * @return list of shipments information
     */
    public ArrayList<Shipment> getShipments() {
        return shipments;
    }

    /**
     * @param shipments list of shipments information
     * @return the merchant order
     */
    public MerchantOrder setShipments(ArrayList<Shipment> shipments) {
        this.shipments = shipments;
        return this;
    }

    /**
     * @param shipment shipment information
     * @return the merchant order
     */
    public MerchantOrder appendShipment(Shipment shipment) {
        if (shipments == null) {
            shipments = new ArrayList<Shipment>();
        }
        shipments.add(shipment);
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
     * @return the merchant order
     */
    public MerchantOrder setNotificationUrl(String notificationUrl) {
        this.notificationUrl = notificationUrl;
        return this;
    }

    /**
     * @return additional info
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * @param additionalInfo additional info
     * @return the merchant order
     */
    public MerchantOrder setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
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
     * @return the merchant order
     */
    public MerchantOrder setExternalReference(String externalReference) {
        this.externalReference = externalReference;
        return this;
    }

    /**
     * @return marketplace
     */
    public String getMarketplace() {
        return marketplace;
    }

    /**
     * @param marketplace marketplace
     * @return the merchant order
     */
    public MerchantOrder setMarketplace(String marketplace) {
        this.marketplace = marketplace;
        return this;
    }

    /**
     * @return total amount
     */
    public Float getTotalAmount() {
        return totalAmount;
    }

    /**
     * Finds a merchant order by your ID
     * @param id merchant order ID
     * @return the merchant order
     * @throws MPException an error if the request fails
     */
    public static MerchantOrder findById(String id) throws MPException {
        return findById(id, WITHOUT_CACHE);
    }

    /**
     * Finds a merchant order by your ID
     * @param id merchant order ID
     * @param useCache true if will use cache, otherwise false
     * @return the merchant order
     * @throws MPException an error if the request fails
     */
    public static MerchantOrder findById(String id, Boolean useCache) throws MPException {
        return findById(id, useCache, MPRequestOptions.createDefault());
    }

    /**
     * Finds a merchant order by your ID
     * @param id merchant order ID
     * @param useCache true if will use cache, otherwise false
     * @param requestOptions request options
     * @return the merchant order
     * @throws MPException an error if the request fails
     */
    @GET(path="/merchant_orders/:id")
    public static MerchantOrder findById(String id, Boolean useCache, MPRequestOptions requestOptions) throws MPException {
        return processMethod(MerchantOrder.class, "findById", useCache, requestOptions, id);
    }

    /**
     * Saves a new merchant order
     * @return the saved merchant order
     * @throws MPException an error if the request fails
     */
    public MerchantOrder save() throws MPException {
        return save(MPRequestOptions.createDefault());
    }

    /**
     * Saves a new merchant order
     * @param requestOptions request options
     * @return the saved merchant order
     * @throws MPException an error if the request fails
     */
    @POST(path="/merchant_orders")
    public MerchantOrder save(MPRequestOptions requestOptions) throws MPException {
        return super.processMethod("save", WITHOUT_CACHE, requestOptions);
    }

    /**
     * Updates a merchant order
     * @return the updated merchant order
     * @throws MPException an error if the request fails
     */
    public MerchantOrder update() throws MPException {
        return update(MPRequestOptions.createDefault());
    }

    /**
     * Updates a merchant order
     * @param requestOptions request options
     * @return the updated merchant order
     * @throws MPException an error if the request fails
     */
    @PUT(path="/merchant_orders/:id")
    public MerchantOrder update(MPRequestOptions requestOptions) throws MPException {
        return super.processMethod("update", WITHOUT_CACHE, requestOptions);
    }

}
