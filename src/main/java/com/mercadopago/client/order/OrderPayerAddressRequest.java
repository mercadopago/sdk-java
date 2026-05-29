package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: acd67b14-97c4-4a4a-840d-0a018c09654f

/**
 * Request object representing the payer's address. Contains street-level address details
 * used for billing, fraud verification, and delivery purposes.
 */
@Getter
@Builder
public class OrderPayerAddressRequest {
    /** Name of the street. */
    private String streetName;

    /** Street number. */
    private String streetNumber;

    /** Postal or zip code. */
    private String zipCode;

    /** Name of the neighborhood or district. */
    private String neighborhood;

    /** Name of the city. */
    private String city;

    /** Name of the state or province. */
    private String state;

    /** Apartment number, floor, or other additional address information. Type: String. */
    private String complement;
}
