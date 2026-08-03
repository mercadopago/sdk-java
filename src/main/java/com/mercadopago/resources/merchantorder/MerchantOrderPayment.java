package com.mercadopago.resources.merchantorder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Getter;

/**
 * Represents a payment associated with a merchant order in the MercadoPago API.
 *
 * <p>Contains payment details such as transaction amount, status, currency, approval date, and
 * refund information. A merchant order may have multiple payments.
 *
 * @see MerchantOrder
 */
@Getter
public class MerchantOrderPayment {
  /** Unique identifier of the payment. */
  private Long id;

  /** Transaction amount (product cost) of the payment. */
  private BigDecimal transactionAmount;

  /** Total amount paid including shipping and other charges. */
  private BigDecimal totalPaidAmount;

  /** Shipping cost included in the payment. */
  private BigDecimal shippingCost;

  /** Currency identifier in ISO 4217 format (e.g., BRL, ARS, USD). */
  private String currencyId;

  /** Current status of the payment (e.g., approved, pending, rejected). */
  private String status;

  /** Detailed information about the payment status or rejection cause. */
  private String statusDetails;

  /** Type of operation (e.g., regular_payment, money_transfer). */
  private String operationType;

  /** Date and time when the payment was approved. */
  private OffsetDateTime dateApproved;

  /** Date and time when the payment was created. */
  private OffsetDateTime dateCreated;

  /** Date and time when the payment was last modified. */
  private OffsetDateTime lastModified;

  /** Total amount refunded from this payment. */
  private BigDecimal amountRefunded;
}
