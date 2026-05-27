package com.mercadopago.client.advancedpayment;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * Request to create a new advanced (split) payment.
 *
 * @see com.mercadopago.client.advancedpayment.AdvancedPaymentClient
 */
@Getter
@Builder
public class AdvancedPaymentCreateRequest {

  /** List of payment sources (card tokens, amounts). */
  private final List<AdvancedPaymentPaymentRequest> payments;

  /** List of receivers (collector IDs and amounts). */
  private final List<AdvancedPaymentDisbursementRequest> disbursements;

  /** Buyer information. */
  private final AdvancedPaymentPayerRequest payer;

  /** MercadoPago application identifier. */
  private final String applicationId;

  /** Human-readable description of the payment. */
  private final String description;

  /** Integrator-provided external reference. */
  private final String externalReference;

  /** When false, creates an authorised but uncaptured payment. */
  private final Boolean capture;

  /** When true, the payment is approved or rejected immediately. */
  private final Boolean binaryMode;
}
