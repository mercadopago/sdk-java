package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TravelPassengerRequest {
    private String firstName;
    private String lastName;
    private PassengerIdentification identification;
}