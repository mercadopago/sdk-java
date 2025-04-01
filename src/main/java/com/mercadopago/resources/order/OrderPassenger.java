package com.mercadopago.resources.order;

import lombok.Getter;

/** OrderPassenger class. */
@Getter
public class OrderPassenger {

    /** Passenger first name. */
    private String firstName;

    /** Passenger last name. */
    private String lastName;

    /** Passenger identification type. */
    private String identificationType;

    /** Passenger identification number. */
    private String identificationNumber;
}
