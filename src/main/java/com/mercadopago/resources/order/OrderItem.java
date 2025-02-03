package com.mercadopago.resources.order;

import lombok.Getter;

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

    /** Category Id of the item. */
    private String categoryId;

    /** Description of the item. */
    private String description;

    /** Picture URL of the item. */
    private String pictureUrl;

}
