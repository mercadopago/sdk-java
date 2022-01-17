package com.mercadopago.resources.customer;

import lombok.Data;

@Data
public class CustomerCardSecurityCode {
    private Integer length;

    private String cardLocation;
}
