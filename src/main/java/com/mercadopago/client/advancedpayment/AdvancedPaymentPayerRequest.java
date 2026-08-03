package com.mercadopago.client.advancedpayment;

import lombok.Builder;
import lombok.Getter;

/**
 * Payer (buyer) information for an advanced payment.
 *
 * @see AdvancedPaymentCreateRequest
 */
@Getter
@Builder
public class AdvancedPaymentPayerRequest {

  /** Payer type. */
  private final String type;

  /** MercadoPago payer identifier. */
  private final String id;

  /** Payer email address. */
  private final String email;

  /** Payer first name. */
  private final String firstName;

  /** Payer last name. */
  private final String lastName;
}
