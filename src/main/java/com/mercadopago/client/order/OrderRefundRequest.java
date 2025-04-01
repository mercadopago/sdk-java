package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

/** OrderRefundRequest class. */
@Builder
@Getter
public class OrderRefundRequest {

        /** Refund transactions. */
        private List<OrderRefundPaymentRequest> transactions;
}

