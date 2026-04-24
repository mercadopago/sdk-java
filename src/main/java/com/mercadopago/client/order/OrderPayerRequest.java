package com.mercadopago.client.order;

import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.resources.common.Phone;
import lombok.Builder;
import lombok.Getter;

// API version: acd67b14-97c4-4a4a-840d-0a018c09654f

/**
 * Request object containing the payer's personal information for an order. Includes contact
 * details such as email and phone, identification documents, and address data.
 */
@Getter
@Builder
public class OrderPayerRequest {
    /** Entity type of the payer (e.g. "individual", "association"). */
    private String entityType;

    /** Email address of the payer. */
    private String email;

    /** First name of the payer. */
    private String firstName;

    /** MercadoPago customer ID associated with the payer. */
    private String customerId;

    /** Last name of the payer. */
    private String lastName;

    /** Payer's identification document (type and number). */
    private IdentificationRequest identification;

    /** Payer's phone number details. */
    private Phone phone;

    /** Payer's address information. */
    private OrderPayerAddressRequest address;

}
