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

    /** Identification information. */
    private Identification identification;

    /** Phone information. */
    private Phone phone;

    /** Address information. */
    private Address address;
}
