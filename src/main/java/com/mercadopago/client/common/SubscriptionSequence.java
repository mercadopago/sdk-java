package com.mercadopago.client.common;

import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO representing the sequence tracking information for a recurring subscription payment.
 * Indicates the current installment number and the total number of planned installments in the
 * subscription lifecycle.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/subscriptions">Subscriptions API Reference</a>
 */
@Getter
@Builder
public class SubscriptionSequence {

    /** Current sequence number of this payment within the subscription (e.g., 3 of 12). */
    private Integer number;

    /** Total number of planned payments in the subscription series. */
    private Integer total;
}
