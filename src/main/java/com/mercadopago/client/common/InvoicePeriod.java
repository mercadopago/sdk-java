package com.mercadopago.client.common;


import lombok.Builder;
import lombok.Getter;

/**
 * Request DTO representing the billing period configuration for subscription invoices. Defines
 * the frequency type and interval at which recurring charges are generated for a subscription plan.
 *
 * @see <a href="https://www.mercadopago.com.br/developers/en/reference/subscriptions">Subscriptions API Reference</a>
 */
@Getter
@Builder
public class InvoicePeriod {

    /** Frequency type of the billing period (e.g., "monthly", "daily"). */
    private String type;

    /** Number of frequency units between each billing cycle (e.g., 1 for every month). */
    private Integer period;
}
