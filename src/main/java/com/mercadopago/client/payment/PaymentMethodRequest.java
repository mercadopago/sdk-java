package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object specifying the payment method details for a payment.
 * Wraps the payment method type along with its associated data such as
 * rules (discounts, fines, interest) and 3-D Secure authentication.
 */
@Getter
@Builder
public class PaymentMethodRequest {

  /** Payment method type (e.g. "credit_card", "debit_card", "bank_transfer"). */
  private final String type;

  /** Data associated with the payment method, including rules and authentication. */
  private final PaymentDataRequest data;
}
