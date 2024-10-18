package com.mercadopago.client.order;

import lombok.Getter;

/** OrderCouponRequest class. */
@Getter
public class OrderCouponRequest {
    /** Coupon code. */
    private String code;

    /** Amount of the coupon discount. */
    private String amount;
}
