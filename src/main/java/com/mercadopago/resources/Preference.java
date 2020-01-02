package com.mercadopago.resources;

import com.google.gson.JsonObject;
import com.mercadopago.MercadoPago;
import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.core.annotations.rest.PUT;
import com.mercadopago.core.annotations.validation.NotNull;
import com.mercadopago.core.annotations.validation.Size;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.datastructures.preference.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Mercado Pago MercadoPago
 * This resource allows you to set up, during the Payment process, all the item information,
 * any accepted means of Payment and many other options.
 *
 * Created by Eduardo Paoletta on 11/9/16.
 */
public class Preference extends MPBase {

    @NotNull
    private ArrayList<Item> items = null;
    @NotNull
    private Payer payer = null;
    private PaymentMethods paymentMethods = null;
    private Shipments shipments = null;
    private BackUrls backUrls = null;
    @Size(max=500) private String notificationUrl = null;
    private String id = null;
    private String initPoint = null;
    private String sandboxInitPoint = null;
    private Date dateCreated = null;
    private OperationType operationType = null;
    private JsonObject metadata = null;

    public enum OperationType {
        regular_payment,
        money_transfer,
        pos_payment
    }
    @Size(max=600) private String additionalInfo = null;
    private AutoReturn autoReturn = null;
    public enum AutoReturn {
        approved,
        all
    }
    @Size(max=256) private String externalReference = null;
    private Boolean expires = null;
    private Date expirationDateFrom = null;
    private Date expirationDateTo = null;
    private Float collectorId = null;
    private Float clientId = null;
    @Size(max=256) private String marketplace = null;
    private Float marketplaceFee = null;
    private DifferentialPricing differentialPricing = null;
    private String sponsorId = null;
    public enum ProcessingMode {
        aggregator,
        gateway
    }
    private ArrayList<ProcessingMode> processingModes = null;
    private Boolean binaryMode = null;
    private ArrayList<Tax> taxes = null;
    private ArrayList<Track> tracks = null;

    public ArrayList<Item> getItems() {
        return items;
    }

    public Preference setItems(ArrayList<Item> items) {
        this.items = items;
        return this;
    }

    public Preference appendItem(Item item) {
        if (items == null) {
            items = new ArrayList<Item>();
        }
        items.add(item);
        return this;
    }

    public Payer getPayer() {
        return payer;
    }

    public Preference setPayer(Payer payer) {
        this.payer = payer;
        return this;
    }

    public PaymentMethods getPaymentMethods() {
        return paymentMethods;
    }

    public Preference setPaymentMethods(PaymentMethods paymentMethods) {
        this.paymentMethods = paymentMethods;
        return this;
    }

    public Shipments getShipments() {
        return shipments;
    }

    public Preference setShipments(Shipments shipments) {
        this.shipments = shipments;
        return this;
    }

    public BackUrls getBackUrls() {
        return backUrls;
    }

    public Preference setBackUrls(BackUrls backUrls) {
        this.backUrls = backUrls;
        return this;
    }

    public String getNotificationUrl() {
        return notificationUrl;
    }

    public Preference setNotificationUrl(String notificationUrl) {
        this.notificationUrl = notificationUrl;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getInitPoint() {
        return initPoint;
    }

    public String getSandboxInitPoint() {
        return sandboxInitPoint;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public Preference setOperationType(OperationType operationType){
        this.operationType = operationType;
        return this;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public Preference setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }

    public AutoReturn getAutoReturn() {
        return autoReturn;
    }

    public Preference setAutoReturn(AutoReturn autoReturn) {
        this.autoReturn = autoReturn;
        return this;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public Preference setExternalReference(String externalReference) {
        this.externalReference = externalReference;
        return this;
    }

    public Boolean getExpires() {
        return expires;
    }

    public Preference setExpires(Boolean expires) {
        this.expires = expires;
        return this;
    }

    public Date getExpirationDateFrom() {
        return expirationDateFrom;
    }

    public Preference setExpirationDateFrom(Date expirationDateFrom) {
        this.expirationDateFrom = expirationDateFrom;
        return this;
    }

    public Date getExpirationDateTo() {
        return expirationDateTo;
    }

    public Preference setExpirationDateTo(Date expirationDateTo) {
        this.expirationDateTo = expirationDateTo;
        return this;
    }

    public Float getCollectorId() {
        return collectorId;
    }

    public Float getClientId() {
        return clientId;
    }

    public String getMarketplace() {
        return marketplace;
    }

    public Preference setMarketplace(String marketplace) {
        this.marketplace = marketplace;
        return this;
    }

    public Float getMarketplaceFee() {
        return marketplaceFee;
    }

    public Preference setMarketplaceFee(Float marketplaceFee) {
        this.marketplaceFee = marketplaceFee;
        return this;
    }

    public DifferentialPricing getDifferentialPricing() {
        return differentialPricing;
    }

    public Preference setDifferentialPricing(DifferentialPricing differentialPricing) {
        this.differentialPricing = differentialPricing;
        return this;
    }

    public String getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(String sponsorId) {
        this.sponsorId = sponsorId;
    }

    public ArrayList<ProcessingMode> getProcessingModes() {
        return processingModes;
    }

    public Preference setProcessingModes(ArrayList<ProcessingMode> processingModes) {
        this.processingModes = processingModes;
        return this;
    }

    public Preference appendProcessingModes(ProcessingMode processingMode) {
        if (processingModes == null) {
            processingModes = new ArrayList<ProcessingMode>();
        }
        processingModes.add(processingMode);
        return this;
    }

    public Boolean getBinaryMode() {
        return binaryMode;
    }

    public Preference setBinaryMode(Boolean binaryMode) {
        this.binaryMode = binaryMode;
        return this;
    }

    public ArrayList<Tax> getTaxes() { return this.taxes; }

    public Preference setTaxes(ArrayList<Tax> taxes) {
        this.taxes = taxes;
        return this;
    }

    public Preference appendTax(Tax tax) {
        if (this.taxes == null) {
            this.taxes = new ArrayList<Tax>();
        }
        this.taxes.add(tax);
        return this;
    }

    public JsonObject getMetadata() {
        return metadata;
    }

    public Preference setMetadata(JsonObject metadata) {
        this.metadata = metadata;
        return this;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public Preference setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
        return this;
    }

    public Preference appendTrack(Track track) {
        if (this.tracks == null) {
            this.tracks = new ArrayList<Track>();
        }
        this.tracks.add(track);
        return this;
    }

    public static Preference findById(String id) throws MPException {
        return findById(id, WITHOUT_CACHE);
    }

    public static Preference findById(String id, Boolean useCache) throws MPException {
        return findById(id, useCache, MPRequestOptions.createDefault());
    }

    @GET(path="/checkout/preferences/:id")
    public static Preference findById(String id, Boolean useCache, MPRequestOptions requestOptions) throws MPException {
        return processMethod(Preference.class, "findById", useCache, requestOptions, id);
    }

    public Preference save() throws MPException {
        return save(MPRequestOptions.createDefault());
    }

    @POST(path="/checkout/preferences")
    public Preference save(MPRequestOptions requestOptions) throws MPException {
        if (requestOptions == null) {
            requestOptions = MPRequestOptions.createDefault();
        }
        addTrackingHeaders(requestOptions);
        return processMethod("save", WITHOUT_CACHE, requestOptions);
    }

    public Preference update() throws MPException {
        return update(MPRequestOptions.createDefault());
    }

    @PUT(path="/checkout/preferences/:id")
    public Preference update(MPRequestOptions requestOptions) throws MPException {
        return processMethod("update", WITHOUT_CACHE, requestOptions);
    }

}
