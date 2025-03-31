package com.mercadopago.client.order;

import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

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

    /** Description of the item. */
    private String description;

    /** Picture URL of the item. */
    private String pictureUrl;

}
