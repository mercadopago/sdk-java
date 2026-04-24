package com.mercadopago.resources.order;

import lombok.Getter;

/**
 * Resource representing the delivery address for a MercadoPago Order shipment.
 * Contains the full postal address where the purchased items will be delivered.
 */
@Getter
public class OrderReceiverAddress {

    /** Name of the street for the delivery address. */
    private String streetName;

    /** Street number of the delivery address. */
    private String streetNumber;

    /** Postal or ZIP code of the delivery address. */
    private String zipCode;

    /** City name of the delivery address. */
    private String cityName;

    /** State or province name of the delivery address. */
    private String stateName;

    /** Floor number within the building, if applicable. */
    private String floor;

    /** Apartment or unit number within the building, if applicable. */
    private String apartment;
}
