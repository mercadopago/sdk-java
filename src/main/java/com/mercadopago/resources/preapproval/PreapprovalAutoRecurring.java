package com.mercadopago.resources.preapproval;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;

/** Recurring data. */
@Getter
public class PreapprovalAutoRecurring {
  /** Currency ID. */
  private String currencyId;

  /** Recurring amount. */
  private BigDecimal transactionAmount;

  /** Recurring frequency. */
  private Integer frequency;

  /** Recurring frequency type (days or months). */
  private String frequencyType;

  /** Recurring start date. */
  private Date startDate;

  /** Recurring end date. */
  private Date endDate;
}
