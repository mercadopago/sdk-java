package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object representing a free shipping method available to the buyer.
 */
@Getter
@Builder
public class OrderFreeShippingMethodRequest {

    /** ID of the free shipping method. */
    private Integer id;
}
