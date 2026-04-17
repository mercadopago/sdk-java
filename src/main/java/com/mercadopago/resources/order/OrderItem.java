package com.mercadopago.resources.order;

import java.util.List;
import lombok.Getter;

// API version: acd67b14-97c4-4a4a-840d-0a018c09654f

/** OrderItem class. */
@Getter
public class OrderItem {

    /** Title of the item. */
    private String title;

    /** Unit price of the item. */
    private String unitPrice;

    /** Quantity of the item. */
    private Integer quantity;

    /** ID of the item. */
    private String id;

    /** Category ID of the item. */
    private String categoryId;

    /** Type of the item. */
    private String type;

    /** Description of the item. */
    private String description;

    /** Picture URL of the item. */
    private String pictureUrl;

    /** External Code of the item. */
    private String externalCode;

    /** Warranty of the item. */
    private Boolean warranty;

    /** Event date of the item. */
    private String eventDate;

    /** Unit measure of the item. */
    private String unitMeasure;

    /** External categories of the item. */
    private List<OrderExternalCategory> externalCategories;
}
