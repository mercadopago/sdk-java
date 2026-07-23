package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/** OrderTransactionSecurityRequest class. */
@Getter
@Builder
public class OrderTransactionSecurityRequest {

    /** Validation. */
    private String validation;

    /** Liability shift. */
    private String liabilityShift;
}
