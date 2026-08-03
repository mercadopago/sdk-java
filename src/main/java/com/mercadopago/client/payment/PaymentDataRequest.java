package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object containing additional data associated with a payment method.
 * Groups the financial rules (discounts, fines, interest) and 3-D Secure
 * authentication details for the chosen payment method.
 */
@Getter
@Builder
public class PaymentDataRequest {

  /** Financial rules applied to the payment (discounts, fines, interest). */
  private final PaymentRulesRequest rules;

  /** 3-D Secure authentication data for the payment. */
  private final PaymentAuthenticationRequest authentication;
}
