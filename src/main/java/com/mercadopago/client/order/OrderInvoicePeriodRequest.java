package com.mercadopago.client.order;

import lombok.Builder;
import lombok.Getter;

// API version: 1ff4822a-2dfd-4393-800e-a562edb3fe32

/**
 * Request object representing the invoice period for a subscription payment. Defines the
 * billing cycle type and duration used to describe the interval covered by each invoice.
 */
@Getter
@Builder
public class OrderInvoicePeriodRequest {

  /** Type of the billing period (e.g. "daily", "monthly", "yearly"). */
  private String type;

  /** Number of periods (e.g. 1 for monthly, 3 for quarterly). */
  private Integer period;

}