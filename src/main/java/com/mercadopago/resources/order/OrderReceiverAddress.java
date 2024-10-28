package com.mercadopago.resources.order;

import lombok.Getter;

/** OrderReceiverAddress class. */
@Getter
public class OrderReceiverAddress {

    /** Street name. */
    private String streetName;

    /** Street number. */
    private String streetNumber;

    /** Zip code. */
    private String zipCode;

    /** City name. */
    private String cityName;

    /** State name. */
    private String stateName;

    /** Floor. */
    private String floor;

    /** Apartment. */
    private String apartment;
}
