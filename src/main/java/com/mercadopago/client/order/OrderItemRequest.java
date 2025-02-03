package com.mercadopago.client.order;

import lombok.Getter;

// API version: b950ae02-4f49-4686-9ad3-7929b21b6495

/** OrderItemRequest class. */
@Getter
public class OrderItemRequest {

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

    /** Description of the item. */
    private String description;

    /** Picture URL of the item. */
    private String pictureUrl;

}
