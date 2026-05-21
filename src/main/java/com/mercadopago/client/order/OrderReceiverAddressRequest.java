package com.mercadopago.client.order;

import lombok.Getter;

/**
 * Request object representing the receiver's address for an order shipment. Contains the
 * full street address, city, state, zip code, and optional floor/apartment details.
 */
@Getter
public class OrderReceiverAddressRequest {

    /** Name of the street. */
    private String streetName;

    /** Street number. */
    private String streetNumber;

    /** Postal or zip code. */
    private String zipCode;

    /** Name of the city. */
    private String cityName;

    /** Name of the state or province. */
    private String stateName;

    /** Floor number or identifier within the building. */
    private String floor;

    /** Apartment number or identifier. */
    private String apartment;
}
