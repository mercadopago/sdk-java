package com.mercadopago.resources.order;

import lombok.Getter;

/** OrderItem class. */
@Getter
public class OrderItem {

    /** Title of the item. */
    private String title;

    /** Description of the item. */
    private String description;

    /** Unit price of the item. */
    private String unitPrice;

    /** Code of the item. */
    private String code;

    /** Type of the item. */
    private String type;

    /** Picture URL of the item. */
    private String pictureUrl;

    /** Quantity of the item. */
    private int quantity;

    /** True if you purchase the item with warranty, false if not. */
    private Boolean warranty;

    /** Category Descriptor information */
    private OrderCategoryDescriptor categoryDescriptor;
}
