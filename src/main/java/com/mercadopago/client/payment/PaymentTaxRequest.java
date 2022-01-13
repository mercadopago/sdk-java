package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Data;

/**
 * PaymentTaxRequest class.
 */
@Data
@Builder
public class PaymentTaxRequest {
  private String type;

  private double value;
}
