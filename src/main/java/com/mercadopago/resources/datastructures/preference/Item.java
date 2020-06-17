package com.mercadopago.resources.datastructures.preference;

import java.util.Date;

import com.mercadopago.core.annotations.validation.NotNull;
import com.mercadopago.core.annotations.validation.Numeric;
import com.mercadopago.core.annotations.validation.Size;

/**
 * Mercado Pago MercadoPago
 * Preference Item class
 *
 * Created by Eduardo Paoletta on 12/2/16.
 */
public class Item {

    @Size(max=256) private String id = null;
    @Size(max=256) private String title = null;
    @Size(max=256) private String description = null;
    @Size(max=600) private String pictureUrl = null;
    @Size(max=256) private String categoryId = null;
    @NotNull @Numeric(min=1) private Integer quantity = null;
    @Size(min=3, max=3) private String currencyId = null;
    @NotNull private Float unitPrice = 0f;
    private Passenger passenger = null;
    private Route route = null;
    private Boolean warranty = null;
    private Date eventDate = null;

    public String getId() {
        return id;
    }

    public Item setId(final String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Item setTitle(final String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Item setDescription(final String description) {
        this.description = description;
        return this;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public Item setPictureUrl(final String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public Item setCategoryId(final String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Item setQuantity(final Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public Item setCurrencyId(final String currencyId) {
        this.currencyId = currencyId;
        return this;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public Item setUnitPrice(final Float unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public Passenger getPassenger() {
        return passenger;
    }
    
    public Item setPassenger(Passenger passenger) {
        this.passenger = passenger;
        return this;
    }

    public Route getRoute() {
        return route;
    }

    public Item setRoute(Route route) {
        this.route = route;
        return this;
    }

    public Boolean getWarranty() {
        return warranty;
    }

    public Item setWarranty(Boolean warranty) {
        this.warranty = warranty;
        return this;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public Item setEventDate(Date eventDate) {
        this.eventDate = eventDate;
        return this;
    }

}
