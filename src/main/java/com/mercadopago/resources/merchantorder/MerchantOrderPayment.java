package com.mercadopago.resources.merchantorder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Getter;

/** Payment information. */
@Getter
public class MerchantOrderPayment {
  /** Payment ID. */
  private Long id;

  /** Product cost. */
  private BigDecimal transactionAmount;

  /** Total amount paid. */
  private BigDecimal totalPaidAmount;

  /** Shipping fee. */
  private BigDecimal shippingCost;

  /** ID of the currency used in payment. */
  private String currencyId;

  /** Payment status. */
  private String status;

  /** Gives more detailed information on the current state or rejection cause. */
  private String statusDetails;

  /** Operation type. */
  private String operationType;

  /** Approval date. */
  private OffsetDateTime dateApproved;

  /** Date of creation. */
  private OffsetDateTime dateCreated;

  /** Last modified date. */
  private OffsetDateTime lastModified;

  /** Amount refunded in this payment. */
  private BigDecimal amountRefunded;
}
