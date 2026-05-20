package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/**
 * Identification document for a travel passenger. Contains the document type and number
 * used to verify the passenger's identity in travel-related transactions.
 */
@Getter
@Builder
public class PassengerIdentification {
    /** Type of the identification document (e.g. "CPF", "DNI", "passport"). */
    private String type;

    /** Number of the identification document. */
    private String number;
}
