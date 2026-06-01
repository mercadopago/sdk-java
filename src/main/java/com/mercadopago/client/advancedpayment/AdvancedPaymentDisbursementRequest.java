package com.mercadopago.client.advancedpayment;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/**
 * A single disbursement receiver in an advanced payment create request.
 *
 * @see AdvancedPaymentCreateRequest
 */
@Getter
@Builder
public class AdvancedPaymentDisbursementRequest {

  /** MercadoPago user identifier of the seller who receives the funds. */
  private final Long collectorId;

  /** Amount to disburse to this collector. */
  private final BigDecimal amount;

  /** Integrator-provided external reference for this disbursement. */
  private final String externalReference;

  /** Marketplace application fee retained from this disbursement. */
  private final BigDecimal applicationFee;

  /** Scheduled date when funds are released to the seller (ISO 8601). */
  private final String moneyReleaseDate;
}
