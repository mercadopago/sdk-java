package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object defining the invoice period for a subscription-based payment.
 * Specifies the billing cycle length and its unit of measurement.
 */
@Getter
@Builder
public class PaymentInvoicePeriodRequest {
  /** Duration of the billing cycle (e.g. 1, 3, 12). */
  private int period;
  /** Unit of the billing cycle (e.g. "monthly", "yearly", "daily"). */
  private String type;
}
