package com.mercadopago.client.advancedpayment;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/**
 * A single payment source within an advanced payment.
 *
 * @see AdvancedPaymentCreateRequest
 */
@Getter
@Builder
public class AdvancedPaymentPaymentRequest {

  /** Payment method identifier (e.g., master, visa). */
  private final String paymentMethodId;

  /** Payment type identifier (e.g., credit_card). */
  private final String paymentTypeId;

  /** Card token for the payment. */
  private final String token;

  /** Expiration date for the payment intent. */
  private final String dateOfExpiration;

  /** Gross transaction amount. */
  private final BigDecimal transactionAmount;

  /** Number of installments. */
  private final Integer installments;

  /** Processing mode (e.g., aggregator). */
  private final String processingMode;

  /** Human-readable description of the payment. */
  private final String description;

  /** Integrator-provided external reference. */
  private final String externalReference;

  /** Text displayed on the cardholder's statement. */
  private final String statementDescriptor;
}
