package com.mercadopago.resources.order;

import lombok.Getter;

/**
 * Resource representing passenger information for travel-related MercadoPago Order items.
 * Contains the traveler's personal identification data required by airlines and
 * travel operators for anti-fraud and regulatory compliance.
 */
@Getter
public class OrderPassenger {

    /** First name of the passenger as it appears on the travel document. */
    private String firstName;

    /** Last name of the passenger as it appears on the travel document. */
    private String lastName;

    /** Type of identification document (e.g., "CPF", "DNI", "passport"). */
    private String identificationType;

    /** Identification document number of the passenger. */
    private String identificationNumber;
}
