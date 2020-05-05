package com.mercadopago.resources.datastructures.advancedpayment;

public class Item {
    private String id;
    private String title;
    private String description;
    private String pictureUrl;
    private String categoryId;
    private Integer quantity;
    private Float unitPrice;

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
        return pictureUrl;
    }

    public Item setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public Item setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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
}
