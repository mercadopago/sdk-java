package com.mercadopago.resources;

import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.annotations.rest.GET;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.exceptions.MPException;

import java.util.Date;

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


    public String getId() {
        return id;
    }

    public CardToken setId(String id) {
        this.id = id;
        return this;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public CardToken setPublicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    public String getCustomerId() {
        return customerId;
    }

    public CardToken setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getCardId() {
        return cardId;
    }

    public CardToken setCardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public CardToken setStatus(String status) {
        this.status = status;
        return this;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public CardToken setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public Date getDateLastUpdate() {
        return dateLastUpdate;
    }

    public CardToken setDateLastUpdate(Date dateLastUpdate) {
        this.dateLastUpdate = dateLastUpdate;
        return this;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public CardToken setDateDue(Date dateDue) {
        this.dateDue = dateDue;
        return this;
    }

    public Boolean getLuhnValidation() {
        return luhnValidation;
    }

    public CardToken setLuhnValidation(Boolean luhnValidation) {
        this.luhnValidation = luhnValidation;
        return this;
    }

    public Boolean getLineMode() {
        return lineMode;
    }

    public CardToken setLineMode(Boolean lineMode) {
        this.lineMode = lineMode;
        return this;
    }

    public Boolean getRequireEsc() {
        return requireEsc;
    }

    public CardToken setRequireEsc(Boolean requireEsc) {
        this.requireEsc = requireEsc;
        return this;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public CardToken save() throws MPException {
        return save(MPRequestOptions.createDefault());
    }

    @POST(path="/v1/card_tokens")
    public CardToken save(MPRequestOptions requestOptions) throws MPException {
        return processMethod("save", WITHOUT_CACHE, requestOptions);
    }

    public static CardToken findById(String id) throws MPException {
        return findById(id, WITHOUT_CACHE, MPRequestOptions.createDefault());
    }

    public static CardToken findById(String id, Boolean useCache) throws MPException {
        return findById(id, useCache, MPRequestOptions.createDefault());
    }

    @GET(path="/v1/card_tokens/:id")
    public static CardToken findById(String id, Boolean useCache, MPRequestOptions requestOptions) throws MPException {
        return processMethod(CardToken.class, "findById", useCache, requestOptions, id);
    }
}
