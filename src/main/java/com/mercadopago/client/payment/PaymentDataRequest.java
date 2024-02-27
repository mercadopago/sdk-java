package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/** PaymentDataRequest class. */
@Getter
@Builder
public class PaymentDataRequest {

  /** Rules. */
  private final PaymentRulesRequest rules;

  /** Authentication. */
  private final PaymentAuthenticationRequest authentication;
}
