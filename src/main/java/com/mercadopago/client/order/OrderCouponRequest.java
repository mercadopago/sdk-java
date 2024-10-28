package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/** OrderCouponRequest class. */
@Builder
@Getter
public class OrderCouponRequest {
    /** Coupon code. */
    private String code;

    /** Amount of the coupon discount. */
    private String amount;
}
