package com.mercadopago.resources.order;

import lombok.Getter;

// API version: d0494f1c-8d81-4c76-ae1d-0c65bb8ef6de

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

    /** Description of the item. */
    private String description;

    /** Picture URL of the item. */
    private String pictureUrl;

    /** External Code of the item. */
    private String externalCode;

}
