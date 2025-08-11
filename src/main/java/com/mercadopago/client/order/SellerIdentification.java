package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Seller identification information.
 */
@Getter
@Builder
public class SellerIdentification {
    /** Identification type. */
    private final String type;

    /** Identification number. */
    private final String number;
}
