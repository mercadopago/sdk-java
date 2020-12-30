package com.mercadopago.resources;

import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.MPResourceArray;
import com.mercadopago.core.annotations.rest.DELETE;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.core.annotations.rest.PUT;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.datastructures.customer.card.Cardholder;
import com.mercadopago.resources.datastructures.customer.card.Issuer;
import com.mercadopago.resources.datastructures.customer.card.PaymentMethod;
import com.mercadopago.resources.datastructures.customer.card.SecurityCode;

import java.util.Date;

/**
 * The cards class is the way to store card data of your customers safely to improve the shopping experience.
 * This will allow your customers to complete their purchases much faster and easily, since they will not have to complete their card data again.
 * This class must be used in conjunction with the Customer class.
 */
public class Card extends MPBase {

    private String token = null;
    private String id = null;
    private String customerId = null;
    private Integer expirationMonth = null;
    private Integer expirationYear = null;
    private String firstSixDigits = null;
    private String lastFourDigits = null;
    private PaymentMethod paymentMethod = null;
    private SecurityCode securityCode = null;
    private Issuer issuer = null;
    private Cardholder cardholder = null;
    private Date dateCreated = null;
    private Date dateLastUpdated = null;
    private String paymentMethodId = null;

    /**
     * @param token card token
     * @return the card
     */
    public Card setToken(String token) {
        this.token = token;
        return this;
    }

    /**
     * @return card ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id card ID
     * @return the card
     */
    public Card setId(String id) {
        this.id = id;
        return this;
    }

    /**
     * @return customer ID
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId customer ID
     * @return the card
     */
    public Card setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    /**
     * @return expiration month
     */
    public Integer getExpirationMonth() {
        return expirationMonth;
    }

    /**
     * @param expirationMonth expiration month
     * @return the card
     */
    public Card setExpirationMonth(Integer expirationMonth) {
        this.expirationMonth = expirationMonth;
        return this;
    }

    /**
     * @return expiration year
     */
    public Integer getExpirationYear() {
        return expirationYear;
    }

    /**
     * @param expirationYear expiration year
     * @return the card
     */
    public Card setExpirationYear(Integer expirationYear) {
        this.expirationYear = expirationYear;
        return this;
    }

    /**
     * @return card first six digits
     */
    public String getFirstSixDigits() {
        return firstSixDigits;
    }

    /**
     * @param firstSixDigits card first six digits
     * @return the card
     */
    public Card setFirstSixDigits(String firstSixDigits) {
        this.firstSixDigits = firstSixDigits;
        return this;
    }

    /**
     * @return card last four digits
     */
    public String getLastFourDigits() {
        return lastFourDigits;
    }

    /**
     * @param lastFourDigits card last four digits
     * @return the card
     */
    public Card setLastFourDigits(String lastFourDigits) {
        this.lastFourDigits = lastFourDigits;
        return this;
    }

    /**
     * @return payment method
     */
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * @param paymentMethod payment method
     * @return the card
     */
    public Card setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    /**
     * @return card security code
     */
    public SecurityCode getSecurityCode() {
        return securityCode;
    }

    /**
     * @param securityCode card security code
     * @return the card
     */
    public Card setSecurityCode(SecurityCode securityCode) {
        this.securityCode = securityCode;
        return this;
    }

    /**
     * @return card issuer
     */
    public Issuer getIssuer() {
        return issuer;
    }

    /**
     * @param issuer card issuer
     * @return the card
     */
    public Card setIssuer(Issuer issuer) {
        this.issuer = issuer;
        return this;
    }

    /**
     * @return cardholder
     */
    public Cardholder getCardholder() {
        return cardholder;
    }

    /**
     * @param cardholder cardholder
     * @return the card
     */
    public Card setCardholder(Cardholder cardholder) {
        this.cardholder = cardholder;
        return this;
    }

    /**
     * @return date of creation
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated date of creation
     * @return the card
     */
    public Card setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    /**
     * @return date of last update
     */
    public Date getDateLastUpdated() {
        return dateLastUpdated;
    }

    /**
     * @param dateLastUpdated date of last update
     * @return the card
     */
    public Card setDateLastUpdated(Date dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
        return this;
    }

    /**
     * @return payment method id
     */
    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    /**
     * @param paymentMethodId payment method ID
     */
    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    /**
     * Get all cards of a customer
     * @see <a href="https://www.mercadopago.com/developers/en/reference/cards/_customers_customer_id_cards/get/">api docs</a>
     * @param customerId customer ID
     * @return the cards
     * @throws MPException an error if the request fails
     */
    public static MPResourceArray all(String customerId) throws MPException {
        return all(customerId, WITHOUT_CACHE);
    }

    /**
     * Get all cards of a customer
     * @see <a href="https://www.mercadopago.com/developers/en/reference/cards/_customers_customer_id_cards/get/">api docs</a>
     * @param customerId customer ID
     * @param useCache true if will use cache, otherwise false
     * @return the cards
     * @throws MPException an error if the request fails
     */
    public static MPResourceArray all(String customerId, Boolean useCache) throws MPException {
        return all(customerId, useCache, MPRequestOptions.createDefault());
    }

    /**
     * Get all cards of a customer
     * @see <a href="https://www.mercadopago.com/developers/en/reference/cards/_customers_customer_id_cards/get/">api docs</a>
     * @param customerId customer ID
     * @param useCache true if will use cache, otherwise false
     * @param requestOptions request options
     * @return the cards
     * @throws MPException an error if the request fails
     */
    @GET(path="/v1/customers/:customer_id/cards")
    public static MPResourceArray all(String customerId, Boolean useCache, MPRequestOptions requestOptions) throws MPException {
        return processMethodBulk(Card.class, "all", useCache, requestOptions, customerId);
    }

    /**
     * Finds a card by your ID
     * @see <a href="https://www.mercadopago.com/developers/en/reference/cards/_customers_customer_id_cards_id/get/">api docs</a>
     * @param customerId customer ID
     * @param id card ID
     * @return the card
     * @throws MPException an error if the request fails
     */
    public static Card findById(String customerId, String id) throws MPException {
        return findById(customerId, id, WITHOUT_CACHE);
    }

    /**
     * Finds a card by your ID
     * @see <a href="https://www.mercadopago.com/developers/en/reference/cards/_customers_customer_id_cards_id/get/">api docs</a>
     * @param customerId customer ID
     * @param id card ID
     * @param useCache true if will use cache, otherwise false
     * @return the card
     * @throws MPException an error if the request fails
     */
    public static Card findById(String customerId, String id, Boolean useCache) throws MPException {
        return findById(customerId, id, useCache, MPRequestOptions.createDefault());
    }

    /**
     * Finds a card by your ID
     * @see <a href="https://www.mercadopago.com/developers/en/reference/cards/_customers_customer_id_cards_id/get/">api docs</a>
     * @param customerId customer ID
     * @param id card ID
     * @param useCache true if will use cache, otherwise false
     * @param requestOptions request options
     * @return the card
     * @throws MPException an error if the request fails
     */
    @GET(path="/v1/customers/:customer_id/cards/:id")
    public static Card findById(String customerId, String id, Boolean useCache, MPRequestOptions requestOptions) throws MPException {
        return processMethod(Card.class, "findById", useCache, requestOptions, customerId, id);
    }

    /**
     * Saves a new card
     * @see <a href="https://www.mercadopago.com/developers/en/reference/cards/_customers_customer_id_cards/post/">api docs</a>
     * @return the saved card
     * @throws MPException an error if the request fails
     */
    public Card save() throws MPException {
        return save(MPRequestOptions.createDefault());
    }

    /**
     * Saves a new card
     * @see <a href="https://www.mercadopago.com/developers/en/reference/cards/_customers_customer_id_cards/post/">api docs</a>
     * @param requestOptions request options
     * @return the saved card
     * @throws MPException an error if the request fails
     */
    @POST(path="/v1/customers/:customer_id/cards/")
    public Card save(MPRequestOptions requestOptions) throws MPException {
        return processMethod("save", WITHOUT_CACHE, requestOptions);
    }

    /**
     * Updates a card
     * @return the updated card
     * @throws MPException an error if the request fails
     */
    public Card update() throws MPException {
        return update(MPRequestOptions.createDefault());
    }

    /**
     * Updates a card
     * @param requestOptions request options
     * @return the updated card
     * @throws MPException an error if the request fails
     */
    @PUT(path="/v1/customers/:customer_id/cards/:id")
    public Card update(MPRequestOptions requestOptions) throws MPException {
        return processMethod("update", WITHOUT_CACHE, requestOptions);
    }

    /**
     * Deletes a card
     * @see <a href="https://www.mercadopago.com/developers/en/reference/cards/_customers_customer_id_cards_id/delete/">api docs</a>
     * @return the deleted card
     * @throws MPException an error if the request fails
     */
    public Card delete() throws MPException {
        return delete(MPRequestOptions.createDefault());
    }

    /**
     * Deletes a card
     * @see <a href="https://www.mercadopago.com/developers/en/reference/cards/_customers_customer_id_cards_id/delete/">api docs</a>
     * @param requestOptions request options
     * @return the deleted card
     * @throws MPException an error if the request fails
     */
    @DELETE(path="/v1/customers/:customer_id/cards/:id")
    public Card delete(MPRequestOptions requestOptions) throws MPException {
        return processMethod("delete", WITHOUT_CACHE, requestOptions);
    }
}
