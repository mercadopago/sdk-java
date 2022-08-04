package com.mercadopago.client.payment;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/** Tax from payment. */
@Getter
@Builder
public class PaymentTaxRequest {
  /** Tax type. */
  private final String type;

  /** Tax value. */
  private final BigDecimal value;

  /** Tax percentage. */
  private final Boolean percentage;
}
