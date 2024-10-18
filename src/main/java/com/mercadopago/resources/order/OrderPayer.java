package com.mercadopago.resources.order;

import com.mercadopago.resources.common.Address;
import com.mercadopago.resources.common.Identification;
import com.mercadopago.resources.common.Phone;
import lombok.Getter;

/** OrderPayer class. */
@Getter
public class OrderPayer {

    /** Payer's email. */
    private String email;

    /** Payer's first name. */
    private String firstName;

    /** Payer's last name. */
    private String lastName;

    /** Type of authentication. */
    private String authenticationType;

    /** Date of registration. */
    private String registrationDate;

    /** Date of last purchase. */
    private String lastPurchase;

    /** Payer's entity type. */
    private String entityType;

    /** If payer is prime user. */
    private Boolean isPrimeUser;

    /** If is first online purchase. */
    private Boolean isFirstPurchaseOnline;

    /** Identification information. */
    private Identification identification;

    /** Phone information. */
    private Phone phone;

    /** Address information. */
    private Address address;
}
