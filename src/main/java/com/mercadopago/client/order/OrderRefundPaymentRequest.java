package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

/** OrderRefundRequest class. */
@Builder
@Getter
public class OrderRefundPaymentRequest {

    /** Payment ID. */
    private String id;

    /** Refund amount. */
    private String amount;
}



