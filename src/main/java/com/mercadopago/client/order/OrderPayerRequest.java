package com.mercadopago.client.order;

import com.mercadopago.resources.common.Identification;
import com.mercadopago.resources.common.Phone;
import lombok.Builder;
import lombok.Getter;

// API version: acd67b14-97c4-4a4a-840d-0a018c09654f

/** OrderPayerRequest class. */
@Getter
@Builder
public class OrderPayerRequest {
    /** Payer's entity type. */
    private String entityType;

    /** Payer's email. */
    private String email;

    /** Payer's first name. */
    private String firstName;

    /** Customer ID. */
    private String customerId;

    /** Payer's last name. */
    private String lastName;

    /** Identification information. */
    private Identification identification;

    /** Phone information. */
    private Phone phone;

    /** Address information. */
    private OrderPayerAddressRequest address;

}
