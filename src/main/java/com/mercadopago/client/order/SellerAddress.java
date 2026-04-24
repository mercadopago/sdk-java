package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Physical address of the seller, provided within the platform additional info section.
 * Contains the full postal address used for logistics and compliance verification.
 */
@Getter
@Builder
public class SellerAddress {
    /** Postal or zip code. */
    private final String zipCode;

    /** Name of the street. */
    private final String streetName;

    /** Street number. */
    private final String streetNumber;

    /** Name of the city. */
    private final String city;

    /** Name of the state or province. */
    private final String state;

    /** Address complement (e.g. suite, floor, building). */
    private final String complement;

    /** Country name or ISO country code. */
    private final String country;
}
