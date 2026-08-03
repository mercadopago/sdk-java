package com.mercadopago.resources.order;

import java.util.List;
import lombok.Getter;

// API version: acd67b14-97c4-4a4a-840d-0a018c09654f

/**
 * Resource representing a line item within a MercadoPago Order.
 * Each item describes a product or service being purchased, including its
 * price, quantity, category, and optional metadata such as picture URL and warranty.
 */
@Getter
public class OrderItem {

    /** Display title of the item shown to the payer. */
    private String title;

    /** Unit price of the item expressed as a decimal string. */
    private String unitPrice;

    /** Number of units of this item included in the order. */
    private Integer quantity;

    /** Unique identifier of the item. */
    private String id;

    /** MercadoPago category identifier for the item. */
    private String categoryId;

    /** Type classification of the item. */
    private String type;

    /** Detailed description of the item. */
    private String description;

    /** URL of the item's picture for display in checkout. */
    private String pictureUrl;

    /** External code used to identify this item in the integrator's catalog. */
    private String externalCode;

    /** Whether the item includes a warranty. */
    private Boolean warranty;

    /** ISO 8601 date associated with the event for event-type items. */
    private String eventDate;

    /** Unit of measure for the item (e.g., "unit", "kg"). */
    private String unitMeasure;

    /** List of external category classifications for this item. */
    private List<OrderExternalCategory> externalCategories;
}
