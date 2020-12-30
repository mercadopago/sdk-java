package com.mercadopago.resources;

import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.exceptions.MPException;

import java.util.Date;

/**
 * This class will allow you to send your customers card data for Mercado Pago server and receive a token to complete the payments transactions.
 */
public class CardToken extends MPBase {

    private String id;
    private String publicKey;
    private String customerId;
    private String cardId;
    private String status;
    private Date dateCreated;
    private Date dateLastUpdate;
    private Date dateDue;
    private Boolean luhnValidation;
    private Boolean lineMode;
    private Boolean requireEsc;
    private String securityCode;


    /**
     * @return card token ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id card token ID
     * @return the card token
     */
    public CardToken setId(String id) {
        this.id = id;
        return this;
    }

    /**
     * @return public key
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     * @param publicKey public key
     * @return the card token
     */
    public CardToken setPublicKey(String publicKey) {
        this.publicKey = publicKey;
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
     * @return the card token
     */
    public CardToken setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    /**
     * @return card ID
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * @param cardId card ID
     * @return the card token
     */
    public CardToken setCardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status status
     * @return the card token
     */
    public CardToken setStatus(String status) {
        this.status = status;
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
     * @return the card token
     */
    public CardToken setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    /**
     * @return date of last update
     */
    public Date getDateLastUpdate() {
        return dateLastUpdate;
    }

    /**
     * @param dateLastUpdate date of last update
     * @return the card token
     */
    public CardToken setDateLastUpdate(Date dateLastUpdate) {
        this.dateLastUpdate = dateLastUpdate;
        return this;
    }

    /**
     * @return due date
     */
    public Date getDateDue() {
        return dateDue;
    }

    /**
     * @param dateDue due date
     * @return the card token
     */
    public CardToken setDateDue(Date dateDue) {
        this.dateDue = dateDue;
        return this;
    }

    /**
     * @return true if uses Luhn Validation, otherwise false
     */
    public Boolean getLuhnValidation() {
        return luhnValidation;
    }

    /**
     * @param luhnValidation true if uses Luhn Validation, otherwise false
     * @return the card token
     */
    public CardToken setLuhnValidation(Boolean luhnValidation) {
        this.luhnValidation = luhnValidation;
        return this;
    }

    /**
     * @return true if uses live mode, otherwise false
     */
    public Boolean getLineMode() {
        return lineMode;
    }

    /**
     * @param lineMode true if uses live mode, otherwise false
     * @return the card token
     */
    public CardToken setLineMode(Boolean lineMode) {
        this.lineMode = lineMode;
        return this;
    }

    /**
     * @return true if require esc, otherwise false
     */
    public Boolean getRequireEsc() {
        return requireEsc;
    }

    /**
     * @param requireEsc true if require esc, otherwise false
     * @return the card token
     */
    public CardToken setRequireEsc(Boolean requireEsc) {
        this.requireEsc = requireEsc;
        return this;
    }

    /**
     * @return security code
     */
    public String getSecurityCode() {
        return securityCode;
    }

    /**
     * @param securityCode security code
     */
    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    /**
     * Saves a new card token
     * @return the card token
     * @throws MPException an error if the request fails
     */
    public CardToken save() throws MPException {
        return save(MPRequestOptions.createDefault());
    }

    /**
     * Saves a new card token
     * @param requestOptions request options
     * @return the card token
     * @throws MPException an error if the request fails
     */
    @POST(path="/v1/card_tokens")
    public CardToken save(MPRequestOptions requestOptions) throws MPException {
        return processMethod("save", WITHOUT_CACHE, requestOptions);
    }

    /**
     * Finds a card token by your ID
     * @param id card token ID
     * @return the card token
     * @throws MPException an error if the request fails
     */
    public static CardToken findById(String id) throws MPException {
        return findById(id, WITHOUT_CACHE, MPRequestOptions.createDefault());
    }

    /**
     * Finds a card token by your ID
     * @param id card token ID
     * @param useCache true if will use cache, otherwise false
     * @return the card token
     * @throws MPException an error if the request fails
     */
    public static CardToken findById(String id, Boolean useCache) throws MPException {
        return findById(id, useCache, MPRequestOptions.createDefault());
    }

    /**
     * Finds a card token by your ID
     * @param id card token ID
     * @param useCache true if will use cache, otherwise false
     * @param requestOptions request options
     * @return the card token
     * @throws MPException an error if the request fails
     */
    @GET(path="/v1/card_tokens/:id")
    public static CardToken findById(String id, Boolean useCache, MPRequestOptions requestOptions) throws MPException {
        return processMethod(CardToken.class, "findById", useCache, requestOptions, id);
    }
}
