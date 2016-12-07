package com.mercadopago.resources.datastructures;

import java.util.Date;

/**
 * Mercado Pago SDK
 * Card class
 *
 * Created by Eduardo Paoletta on 12/2/16.
 */
public class Card {

    private Number id = null;
    private String last_four_digits = null;
    private String first_six_digits = null;
    private Integer expiration_year = null;
    private Integer expiration_month = null;
    private Date date_created = null;
    private Date date_last_updated = null;
    private Cardholder cardholder = null;

    public Number getId() {
        return id;
    }

    public String getLast_four_digits() {
        return last_four_digits;
    }

    public String getFirst_six_digits() {
        return first_six_digits;
    }

    public Integer getExpiration_year() {
        return expiration_year;
    }

    public Integer getExpiration_month() {
        return expiration_month;
    }

    public Date getDate_created() {
        return date_created;
    }

    public Date getDate_last_updated() {
        return date_last_updated;
    }

    public Cardholder getCardholder() {
        return cardholder;
    }

}
