package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object representing a passenger in a travel-related order. Contains the passenger's
 * name and identification document, used for airline and travel industry fraud prevention.
 */
@Getter
@Builder
public class TravelPassengerRequest {
    /** First name of the passenger. */
    private String firstName;

    /** Last name of the passenger. */
    private String lastName;

    /** Identification document of the passenger. */
    private PassengerIdentification identification;
}