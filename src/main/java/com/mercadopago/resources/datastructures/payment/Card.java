package com.mercadopago.resources.datastructures.payment;

import java.util.Date;

/**
 * Mercado Pago SDK
 * Card class
 *
 * Created by Eduardo Paoletta on 12/2/16.
 */
public class Card {

    private Number id = null;
    private String lastFourDigits = null;
    private String firstSixDigits = null;
    private Integer expirationYear = null;
    private Integer expirationMonth = null;
    private Date dateCreated = null;
    private Date dateLastUpdated = null;
    private Cardholder cardholder = null;


    public Number getId() {
        return id;
    }

    public String getLastFourDigits() {
        return lastFourDigits;
    }

    public String getFirstSixDigits() {
        return firstSixDigits;
    }

    public Integer getExpirationYear() {
        return expirationYear;
    }

    public Integer getExpirationMonth() {
        return expirationMonth;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getDateLastUpdated() {
        return dateLastUpdated;
    }

    public Cardholder getCardholder() {
        return cardholder;
    }

}
