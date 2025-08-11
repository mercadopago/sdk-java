package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Seller address information.
 */
@Getter
@Builder
public class SellerAddress {
    /** Zip code. */
    private final String zipCode;

    /** Street name. */
    private final String streetName;

    /** Street number. */
    private final String streetNumber;

    /** City. */
    private final String city;

    /** State. */
    private final String state;

    /** Complement. */
    private final String complement;

    /** Country. */
    private final String country;
}
