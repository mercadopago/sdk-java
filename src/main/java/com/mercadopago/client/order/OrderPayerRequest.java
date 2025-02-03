package com.mercadopago.client.order;

import com.mercadopago.resources.common.Address;
import com.mercadopago.resources.common.Identification;
import com.mercadopago.resources.common.Phone;
import lombok.Builder;
import lombok.Getter;

// API version: b950ae02-4f49-4686-9ad3-7929b21b6495

/** OrderPayerRequest class. */
@Getter
@Builder
public class OrderPayerRequest {

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
    private Address address;

}
