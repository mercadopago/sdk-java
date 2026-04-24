package com.mercadopago.client.payment;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/**
 * Request object representing a fee rule applied to a payment, such as
 * a late-payment fine or interest charge. Used within {@link PaymentRulesRequest}.
 */
@Getter
@Builder
public class PaymentFeeRequest {

  /** Fee type (e.g. "percentage" or "fixed"). */
  private String type;

  /** Fee value, interpreted as a percentage or fixed amount depending on type. */
  private BigDecimal value;
}
