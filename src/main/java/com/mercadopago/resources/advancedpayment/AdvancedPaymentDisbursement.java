package com.mercadopago.resources.advancedpayment;

import java.math.BigDecimal;
import lombok.Getter;

/**
 * A single disbursement receiver within an advanced payment.
 *
 * @see AdvancedPayment
 */
@Getter
public class AdvancedPaymentDisbursement {

  /** Unique disbursement identifier. */
  private Long id;

  /** MercadoPago user identifier of the seller receiving the funds. */
  private Long collectorId;

  /** Amount disbursed to this collector. */
  private BigDecimal amount;

  /** Integrator-provided external reference for this disbursement. */
  private String externalReference;

  /** Marketplace application fee retained from this disbursement. */
  private BigDecimal applicationFee;

  /** Scheduled date when funds are released to the seller. */
  private String moneyReleaseDate;

  /** Current status of this disbursement. */
  private String status;

  /** Additional detail about the disbursement status. */
  private String statusDetail;
}
