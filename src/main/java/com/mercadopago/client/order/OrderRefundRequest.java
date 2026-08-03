package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

/**
 * Request object for initiating a refund on an order. Contains the list of transaction payments
 * to be refunded, each specifying the payment ID and the amount to refund.
 */
@Builder
@Getter
public class OrderRefundRequest {

        /** List of payment refund details within the transaction to be refunded. */
        private List<OrderRefundPaymentRequest> transactions;
}

