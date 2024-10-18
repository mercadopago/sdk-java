package com.mercadopago.client.order;

import lombok.Getter;

/** OrderItemRequest class. */
@Getter
public class OrderItemRequest {

    /** Title of the item. */
    private String title;

    /** Unit price of the item. */
    private String unitPrice;

    /** Quantity of the item. */
    private int quantity;

    /** Description of the item. */
    private String description;

    /** Code of the item. */
    private String code;

    /** Type of the item. */
    private String type;

    /** Picture URL of the item. */
    private String pictureUrl;

    /** True if you purchase the item with warranty, false if not. */
    private Boolean warranty;

    /** Category Descriptor information */
    private OrderCategoryDescriptorRequest categoryDescriptor;
}
