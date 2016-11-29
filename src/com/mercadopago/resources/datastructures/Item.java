package com.mercadopago.resources.datastructures;

import com.mercadopago.core.validationannotations.NotNull;
import com.mercadopago.core.validationannotations.Numeric;
import com.mercadopago.core.validationannotations.Size;

/**
 * Mercado Libre SDK
 * Item class
 *
 * Created by Eduardo Paoletta on 11/23/16.
 */
public class Item {

    @Size(max=256) private String id = null;
    @Size(max=256) private String title = null;
    @Size(max=256) private String description = null;
    @Size(max=600) private String picture_url = null;
    @Size(max=256) private String category_id = null;
    @NotNull @Numeric(min=1) private Integer quantity = null;
    @Size(min=3, max=3) private String currency_id = null;
    @NotNull @Numeric(min=.01f, max=999999f, fractionDigits=2) private Float unit_price = 0f;

    public String getId() {
        return id;
    }

    public Item setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Item setTitle(String title) {
        this.title = title;
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
        return picture_url;
    }

    public Item setPictureUrl(String pictureUrl) {
        this.picture_url = pictureUrl;
        return this;
    }

    public String getCategoryId() {
        return category_id;
    }

    public Item setCategoryId(String categoryId) {
        this.category_id = categoryId;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Item setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getCurrencyId() {
        return currency_id;
    }

    public Item setCurrencyId(String currencyId) {
        this.currency_id = currencyId;
        return this;
    }

    public Float getUnitPrice() {
        return unit_price;
    }

    public Item setUnitPrice(Float unitPrice) {
        this.unit_price = unitPrice;
        return this;
    }

}
