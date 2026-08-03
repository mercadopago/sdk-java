package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: acd67b14-97c4-4a4a-840d-0a018c09654f

/**
 * Request object representing an item being purchased within an order. Contains product details
 * such as title, price, quantity, and optional metadata like category, picture, and warranty.
 */
@Getter
@Builder
public class OrderItemRequest {

    /** Display title of the item. */
    private String title;

    /** Unit price of the item as a decimal string. */
    private String unitPrice;

    /** Quantity of this item being purchased. */
    private Integer quantity;

    /** External code used to identify the item in the integrator's system. */
    private String externalCode;

    /** Category identifier for the item. */
    private String categoryId;

    /** Type classification of the item. */
    private String type;

    /** Detailed description of the item. */
    private String description;

    /** URL of the item's picture. */
    private String pictureUrl;

    /** Whether the item includes a warranty. */
    private Boolean warranty;

    /** Date of the event associated with this item, if applicable. */
    private String eventDate;
}
