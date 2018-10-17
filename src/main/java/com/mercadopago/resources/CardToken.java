package com.mercadopago.resources;

import com.mercadopago.core.MPBase;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.exceptions.MPException;

import java.util.Date;

public class CardToken extends MPBase {

    private String id = null;

    private String cardId = null;

    private Date dateCreated = null;

    private Date dateLastUpdated = null;

    private Date dateDue = null;

    private Boolean luhnValidation = null;

    private Boolean liveMode = null;

    private Boolean requireEsc = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateLastUpdated() {
        return dateLastUpdated;
    }

    public void setDateLastUpdated(Date dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public Boolean getLuhnValidation() {
        return luhnValidation;
    }

    public void setLuhnValidation(Boolean luhnValidation) {
        this.luhnValidation = luhnValidation;
    }

    public Boolean getLiveMode() {
        return liveMode;
    }

    public void setLiveMode(Boolean liveMode) {
        this.liveMode = liveMode;
    }

    public Boolean getRequireEsc() {
        return requireEsc;
    }

    public void setRequireEsc(Boolean requireEsc) {
        this.requireEsc = requireEsc;
    }

    @POST(path="/v1/card_tokens")
    public CardToken save() throws MPException {
        return super.processMethod("save", WITHOUT_CACHE);
    }

    @Override
    public String toString() {
        return "CardToken{" +
                "id='" + id + '\'' +
                ", cardId='" + cardId + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateLastUpdated=" + dateLastUpdated +
                ", dateDue=" + dateDue +
                ", luhnValidation=" + luhnValidation +
                ", liveMode=" + liveMode +
                ", requireEsc=" + requireEsc +
                '}';
    }
}
