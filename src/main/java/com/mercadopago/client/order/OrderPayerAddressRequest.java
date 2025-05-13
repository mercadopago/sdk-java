package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: acd67b14-97c4-4a4a-840d-0a018c09654f

/** OrderPayerAddressRequest class. */
@Getter
@Builder
public class OrderPayerAddressRequest {
    /** Street name. */
    private String streetName;

    /** Street number. */
    private String streetNumber;

    /** Zip code. */
    private String zipCode;

    /** Neighborhood. */
    private String neighborhood;

    /** City. */
    private String city;

    /** State. */
    private String state;
}
