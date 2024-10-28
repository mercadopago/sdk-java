package com.mercadopago.client.order;

import com.mercadopago.resources.common.Address;
import com.mercadopago.resources.common.Identification;
import com.mercadopago.resources.common.Phone;
import lombok.Builder;
import lombok.Getter;

/** OrderPayerRequest class. */
@Getter
@Builder
public class OrderPayerRequest {

    /** Payer's email. */
    private String email;

    /** Payer's first name. */
    private String firstName;

    /** Payer's last name. */
    private String lastName;

    /** Identification information. */
    private Identification identification;

    /** Phone information. */
    private Phone phone;

    /** Address information. */
    private Address address;

    /** Type of authentication. */
    private String authenticationType;

    /** Date of registration. */
    private String registrationDate;

    /** Date of last purchase. */
    private String lastPurchase;

    /** If payer is prime user. */
    private Boolean isPrimeUser;

    /** If is first online purchase. */
    private Boolean isFirstPurchaseOnline;

    /** Payer's entity type. */
    private String entityType;
}
