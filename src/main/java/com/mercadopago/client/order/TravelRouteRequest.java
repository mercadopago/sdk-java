package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TravelRouteRequest {
    private String departure;
    private String destination;
    private String departureDateTime;
    private String arrivalDateTime;
    private String company;
}