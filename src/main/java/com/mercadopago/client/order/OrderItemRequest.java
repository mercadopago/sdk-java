package com.mercadopago.client.order;

import lombok.Getter;

// API version: acd67b14-97c4-4a4a-840d-0a018c09654f

/** OrderItemRequest class. */
@Getter
public class OrderItemRequest {

    /** Title of the item. */
    private String title;

    /** Unit price of the item. */
    private String unitPrice;

    /** Quantity of the item. */
    private Integer quantity;

    /** External Code of the item. */
    private String externalCode;

    /** Category ID of the item. */
    private String categoryId;

    /** Type of the item. */
    private String type;

    /** Description of the item. */
    private String description;

    /** Picture URL of the item. */
    private String pictureUrl;

    /** Warranty of the item. */
    private Boolean warranty;

    /** Event date of the item. */
    private String eventDate;
}
