package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Seller phone information.
 */
@Getter
@Builder
public class SellerPhone {
    /** Area code. */
    private final String areaCode;

    /** Phone number. */
    private final String number;
}
