package com.mercadopago.client.customer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerCardCreateRequest {
    private String token;
}
