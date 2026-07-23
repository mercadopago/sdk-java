package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/** OrderIntegrationDataRequest class. */
@Getter
@Builder
public class OrderIntegrationDataRequest {

    /** Integrator ID. */
    private String integratorId;

    /** Platform ID. */
    private String platformId;

    /** Corporation ID. */
    private String corporationId;

    /** Sponsor information. */
    private OrderSponsorRequest sponsor;
}
