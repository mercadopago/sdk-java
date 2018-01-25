package com.mercadopago.resources.datastructures.merchantorder;

import com.mercadopago.core.annotations.validation.Size;

/**
 * Mercado Pago MercadoPago
 * Merchant Order Item class
 *
 * Created by Eduardo Paoletta on 12/13/16.
 */
public class Item {

    private String id = null;
    private String categoryId = null;
    @Size(min=3, max=3) private String currencyId = null;
    private String description = null;
    private String pictureUrl = null;
    private Integer quantity = null;
    private Float unitPrice = null;
    private String title = null;


    public String getId() {
        return id;
    }

    public Item setId(String id) {
        this.id = id;
        return this;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public Item setCategoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public Item setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Item setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public Item setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Item setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public Item setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Item setTitle(String title) {
        this.title = title;
        return this;
    }

}
