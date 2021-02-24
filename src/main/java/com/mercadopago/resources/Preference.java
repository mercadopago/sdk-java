package com.mercadopago.resources;

import java.util.ArrayList;
import java.util.Date;

import com.google.gson.JsonObject;
import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.core.annotations.rest.PUT;
import com.mercadopago.core.annotations.validation.NotNull;
import com.mercadopago.core.annotations.validation.Size;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.DifferentialPricing;
import com.mercadopago.resources.datastructures.preference.Item;
import com.mercadopago.resources.datastructures.preference.Payer;
import com.mercadopago.resources.datastructures.preference.PaymentMethods;
import com.mercadopago.resources.datastructures.preference.Shipments;
import com.mercadopago.resources.datastructures.preference.Tax;
import com.mercadopago.resources.datastructures.preference.Track;

/**
 * This class will allow you to charge your customers through our web form from any device in a simple, fast and secure way.
 */
public class Preference extends MPBase {

    @NotNull
    private ArrayList<Item> items = null;
    private Payer payer = null;
    private PaymentMethods paymentMethods = null;
    private Shipments shipments = null;
    private BackUrls backUrls = null;
    @Size(max=500) private String notificationUrl = null;
    private String id = null;
    private String initPoint = null;
    private String sandboxInitPoint = null;
    private String purpose = null;
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
    private Date dateOfExpiration = null;
    private Float collectorId = null;
    private Float clientId = null;
    @Size(max=256) private String marketplace = null;
    private Float marketplaceFee = null;
    private DifferentialPricing differentialPricing = null;
    private Integer sponsorId = null;
    public enum ProcessingMode {
        aggregator,
        gateway
    }
    private ArrayList<ProcessingMode> processingModes = null;
    private Boolean binaryMode = null;
    private ArrayList<Tax> taxes = null;
    private ArrayList<Track> tracks = null;

    /**
     * @return preference items
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * @param items preference items
     * @return the preference
     */
    public Preference setItems(ArrayList<Item> items) {
        this.items = items;
        return this;
    }

    /**
     * Append a item into the preference
     * @param item preference item
     * @return the preference
     */
    public Preference appendItem(Item item) {
        if (items == null) {
            items = new ArrayList<Item>();
        }
        items.add(item);
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
     * @return the preference
     */
    public Preference setPayer(Payer payer) {
        this.payer = payer;
        return this;
    }

    /**
     * @return payment methods
     */
    public PaymentMethods getPaymentMethods() {
        return paymentMethods;
    }

    /**
     * @param paymentMethods payment methods
     * @return the preference
     */
    public Preference setPaymentMethods(PaymentMethods paymentMethods) {
        this.paymentMethods = paymentMethods;
        return this;
    }

    /**
     * @return shipments information
     */
    public Shipments getShipments() {
        return shipments;
    }

    /**
     * @param shipments shipments information
     * @return the preference
     */
    public Preference setShipments(Shipments shipments) {
        this.shipments = shipments;
        return this;
    }

    /**
     * @return back URLs
     */
    public BackUrls getBackUrls() {
        return backUrls;
    }

    /**
     * @param backUrls back URLs
     * @return the preference
     */
    public Preference setBackUrls(BackUrls backUrls) {
        this.backUrls = backUrls;
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
     * @return the preference
     */
    public Preference setNotificationUrl(String notificationUrl) {
        this.notificationUrl = notificationUrl;
        return this;
    }

    /**
     * @return preference ID
     */
    public String getId() {
        return id;
    }

    /**
     * @return checkout URL
     */
    public String getInitPoint() {
        return initPoint;
    }

    /**
     * @return sandbox checkout URL
     */
    public String getSandboxInitPoint() {
        return sandboxInitPoint;
    }

    /**
     * @return purpose
     */
    public String getPurpose() {
        return purpose;
    } 
    
    /**
     * @param purpose purpose
     * @return the preference
     */
    public Preference setPurpose(String purpose) {
        this.purpose = purpose;
        return this;
    }

    /**
     * @return date of creation
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @return operation type
     */
    public OperationType getOperationType() {
        return operationType;
    }

    /**
     * @param operationType operation type
     * @return the preference
     */
    public Preference setOperationType(OperationType operationType){
        this.operationType = operationType;
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
     * @return the preference
     */
    public Preference setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }

    /**
     * @return if specified, your buyers will be redirected back to your site immediately after completing the purchase
     */
    public AutoReturn getAutoReturn() {
        return autoReturn;
    }

    /**
     * @param autoReturn if specified, your buyers will be redirected back to your site immediately after completing the purchase
     * @return the preference
     */
    public Preference setAutoReturn(AutoReturn autoReturn) {
        this.autoReturn = autoReturn;
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
     * @return the preference
     */
    public Preference setExternalReference(String externalReference) {
        this.externalReference = externalReference;
        return this;
    }

    /**
     * @return true if the preference expires, otherwise false
     */
    public Boolean getExpires() {
        return expires;
    }

    /**
     * @param expires true if the preference expires, otherwise false
     * @return the preference
     */
    public Preference setExpires(Boolean expires) {
        this.expires = expires;
        return this;
    }

    /**
     * @return date since the preference will be active
     */
    public Date getExpirationDateFrom() {
        return expirationDateFrom;
    }

    /**
     * @param expirationDateFrom date since the preference will be active
     * @return the preference
     */
    public Preference setExpirationDateFrom(Date expirationDateFrom) {
        this.expirationDateFrom = expirationDateFrom;
        return this;
    }

    /**
     * @return date when the preference will be expired
     */
    public Date getExpirationDateTo() {
        return expirationDateTo;
    }

    /**
     * @param expirationDateTo date when the preference will be expired
     * @return the preference
     */
    public Preference setExpirationDateTo(Date expirationDateTo) {
        this.expirationDateTo = expirationDateTo;
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
    public Preference setDateOfExpiration(Date dateOfExpiration) {
        this.dateOfExpiration = dateOfExpiration;
        return this;
    }

    /**
     * @return collector ID
     */
    public Float getCollectorId() {
        return collectorId;
    }

    /**
     * @return client ID
     */
    public Float getClientId() {
        return clientId;
    }

    /**
     * @return marketplace
     */
    public String getMarketplace() {
        return marketplace;
    }

    /**
     * @param marketplace marketplace
     * @return the preference
     */
    public Preference setMarketplace(String marketplace) {
        this.marketplace = marketplace;
        return this;
    }

    /**
     * @return marketplace fee
     */
    public Float getMarketplaceFee() {
        return marketplaceFee;
    }

    /**
     * @param marketplaceFee marketplace fee
     * @return the preference
     */
    public Preference setMarketplaceFee(Float marketplaceFee) {
        this.marketplaceFee = marketplaceFee;
        return this;
    }

    /**
     * @return differential pricing
     */
    public DifferentialPricing getDifferentialPricing() {
        return differentialPricing;
    }

    /**
     * @param differentialPricing differential pricing
     * @return the preference
     */
    public Preference setDifferentialPricing(DifferentialPricing differentialPricing) {
        this.differentialPricing = differentialPricing;
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
     * @return the preference
     */
    public Preference setSponsorId(Integer sponsorId) {
        this.sponsorId = sponsorId;
        return this;
    }

    /**
     * @return processing modes
     */
    public ArrayList<ProcessingMode> getProcessingModes() {
        return processingModes;
    }

    /**
     * @param processingModes processing modes
     * @return the preference
     */
    public Preference setProcessingModes(ArrayList<ProcessingMode> processingModes) {
        this.processingModes = processingModes;
        return this;
    }

    /**
     * Append a processing mode into preference
     * @param processingMode processing mode
     * @return the preference
     */
    public Preference appendProcessingModes(ProcessingMode processingMode) {
        if (processingModes == null) {
            processingModes = new ArrayList<ProcessingMode>();
        }
        processingModes.add(processingMode);
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
     * @return the preference
     */
    public Preference setBinaryMode(Boolean binaryMode) {
        this.binaryMode = binaryMode;
        return this;
    }

    /**
     * @return taxes
     */
    public ArrayList<Tax> getTaxes() { return this.taxes; }

    /**
     * @param taxes taxes
     * @return
     */
    public Preference setTaxes(ArrayList<Tax> taxes) {
        this.taxes = taxes;
        return this;
    }

    /**
     * Append a tax into the preference
     * @param tax tax
     * @return the preference
     */
    public Preference appendTax(Tax tax) {
        if (this.taxes == null) {
            this.taxes = new ArrayList<Tax>();
        }
        this.taxes.add(tax);
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
     * @return the preference
     */
    public Preference setMetadata(JsonObject metadata) {
        this.metadata = metadata;
        return this;
    }

    /**
     * @return tracks
     */
    public ArrayList<Track> getTracks() {
        return tracks;
    }

    /**
     * @param tracks tracks
     * @return the preference
     */
    public Preference setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
        return this;
    }

    /**
     * Append the track into the preference
     * @param track track
     * @return the preference
     */
    public Preference appendTrack(Track track) {
        if (this.tracks == null) {
            this.tracks = new ArrayList<Track>();
        }
        this.tracks.add(track);
        return this;
    }

    /**
     * Finds a preference by your ID
     * @see <a href="https://www.mercadopago.com/developers/en/reference/_checkout_preferences_id/get/">api docs</a>
     * @param id preference ID
     * @return the preference
     * @throws MPException an error if the request fails
     */
    public static Preference findById(String id) throws MPException {
        return findById(id, WITHOUT_CACHE);
    }

    /**
     * Finds a preference by your ID
     * @see <a href="https://www.mercadopago.com/developers/en/reference/_checkout_preferences_id/get/">api docs</a>
     * @param id preference ID
     * @param useCache true if will use cache, otherwise false
     * @return the preference
     * @throws MPException an error if the request fails
     */
    public static Preference findById(String id, Boolean useCache) throws MPException {
        return findById(id, useCache, MPRequestOptions.createDefault());
    }

    /**
     * Finds a preference by your ID
     * @see <a href="https://www.mercadopago.com/developers/en/reference/_checkout_preferences_id/get/">api docs</a>
     * @param id preference ID
     * @param useCache true if will use cache, otherwise false
     * @param requestOptions request options
     * @return the preference
     * @throws MPException an error if the request fails
     */
    @GET(path="/checkout/preferences/:id")
    public static Preference findById(String id, Boolean useCache, MPRequestOptions requestOptions) throws MPException {
        return processMethod(Preference.class, "findById", useCache, requestOptions, id);
    }

    /**
     * Saves a new preference
     * @see <a href="https://www.mercadopago.com/developers/en/reference/preferences/_checkout_preferences/post/">api docs</a>
     * @return the saved preference
     * @throws MPException an error if the request fails
     */
    public Preference save() throws MPException {
        return save(MPRequestOptions.createDefault());
    }

    /**
     * Saves a new preference
     * @see <a href="https://www.mercadopago.com/developers/en/reference/preferences/_checkout_preferences/post/">api docs</a>
     * @param requestOptions request options
     * @return the saved preference
     * @throws MPException an error if the request fails
     */
    @POST(path="/checkout/preferences")
    public Preference save(MPRequestOptions requestOptions) throws MPException {
        if (requestOptions == null) {
            requestOptions = MPRequestOptions.createDefault();
        }
        addTrackingHeaders(requestOptions);
        return processMethod("save", WITHOUT_CACHE, requestOptions);
    }

    /**
     * Updates the preference
     * @see <a href="https://www.mercadopago.com/developers/en/reference/preferences/_checkout_preferences_id/put/">api docs</a>
     * @return the updated preference
     * @throws MPException an error if the request fails
     */
    public Preference update() throws MPException {
        return update(MPRequestOptions.createDefault());
    }

    /**
     * Updates the preference
     * @see <a href="https://www.mercadopago.com/developers/en/reference/preferences/_checkout_preferences_id/put/">api docs</a>
     * @param requestOptions request options
     * @return the updated preference
     * @throws MPException an error if the request fails
     */
    @PUT(path="/checkout/preferences/:id")
    public Preference update(MPRequestOptions requestOptions) throws MPException {
        return processMethod("update", WITHOUT_CACHE, requestOptions);
    }

}
