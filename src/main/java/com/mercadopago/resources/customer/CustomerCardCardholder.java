package com.mercadopago.resources.customer;

import lombok.Data;

@Data
public class CustomerCardCardholder {
    private String name;

    private CustomerCardCardholderIdentification identification;
}
