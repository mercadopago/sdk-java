package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PassengerIdentification {
    private String type;
    private String number;
}
