package com.mercadopago.client.order;

import lombok.Getter;

/** OrderPassengerRequest class. */
@Getter
public class OrderPassengerRequest {

    /** Passenger first name. */
    private String firstName;

    /** Passenger last name. */
    private String lastName;

    /** Passenger identification type. */
    private String identificationType;

    /** Passenger identification number. */
    private String identificationNumber;
}
