package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/** OrderSponsorRequest class. */
@Getter
@Builder
public class OrderSponsorRequest {

    /** Sponsor ID. */
    private String id;
}
