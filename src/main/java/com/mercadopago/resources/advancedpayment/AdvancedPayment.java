package com.mercadopago.resources.advancedpayment;

import com.mercadopago.net.MPResource;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Getter;

/**
 * Resource representing a MercadoPago advanced (split) payment.
 *
 * <p>An advanced payment allows a marketplace to collect a single payment and distribute the
 * funds among multiple sellers (disbursements). Supports two-step flows (authorise → capture)
 * and individual disbursement release-date control.
 *
 * @see com.mercadopago.client.advancedpayment.AdvancedPaymentClient
 */
@Getter
public class AdvancedPayment extends MPResource {

  /** Unique identifier of the advanced payment. */
  private Long id;

  /** Identifier of the MercadoPago application that created this payment. */
  private String applicationId;

  /** Integrator-provided external reference for reconciliation. */
  private String externalReference;

  /** Human-readable description of the payment. */
  private String description;

  /** Overall status of the advanced payment. */
  private String status;

  /** Whether the payment has been captured. */
  private Boolean capture;

  /** When true, the payment is approved or rejected immediately. */
  private Boolean binaryMode;

  /** Timestamp when this advanced payment was created. */
  private OffsetDateTime dateCreated;

  /** Timestamp of the last update. */
  private OffsetDateTime dateLastUpdated;

  /** List of disbursements associated with this advanced payment. */
  private List<AdvancedPaymentDisbursement> disbursements;
}
