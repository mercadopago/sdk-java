package com.mercadopago.resources.invoice;

import com.mercadopago.net.MPResource;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Getter;

/**
 * Resource representing a subscription invoice (authorized payment) in MercadoPago.
 *
 * <p>Each invoice corresponds to a billing cycle of a {@code Preapproval} and tracks the charge
 * amount, status, retry attempts, and the resulting payment.
 *
 * @see com.mercadopago.client.invoice.InvoiceClient
 */
@Getter
public class Invoice extends MPResource {

  /** Unique invoice identifier assigned by MercadoPago. */
  private Long id;

  /** Invoice type (e.g., scheduled). */
  private String type;

  /** Timestamp when the invoice was created. */
  private OffsetDateTime dateCreated;

  /** Timestamp of the last modification. */
  private OffsetDateTime lastModified;

  /** Identifier of the preapproval (subscription) that generated this invoice. */
  private String preapprovalId;

  /** Description or reason for the invoice charge. */
  private String reason;

  /** Integrator-provided external reference for reconciliation. */
  private String externalReference;

  /** ISO 4217 currency code for the invoice amount. */
  private String currencyId;

  /** Amount charged to the subscriber. */
  private BigDecimal transactionAmount;

  /** Scheduled date for the next retry if the payment failed. */
  private OffsetDateTime nextRetryDate;

  /** Scheduled date for the payment debit. */
  private OffsetDateTime debitDate;

  /** Payment method identifier used for this invoice. */
  private String paymentMethodId;

  /** Current retry attempt number. */
  private Integer retryAttempt;

  /** Invoice status (e.g., scheduled, processed, recycling, cancelled). */
  private String status;

  /** Summary description of the invoice status. */
  private String summarized;

  /** Payment details associated with this invoice. */
  private InvoicePayment payment;
}
