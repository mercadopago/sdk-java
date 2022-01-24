package com.mercadopago.resources.merchantorder;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;

/** Payment information. */
@Getter
public class MerchantOrderPayment {
  /** Payment ID. */
  private long id;

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

  /** Approbation date. */
  private Date dateApproved;

  /** Date of creation. */
  private Date dateCreated;

  /** Last modified date. */
  private Date lastModified;

  /** Amount refunded in this payment. */
  private BigDecimal amountRefunded;
}
