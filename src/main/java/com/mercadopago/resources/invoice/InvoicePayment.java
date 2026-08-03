package com.mercadopago.resources.invoice;

import lombok.Getter;

/**
 * Payment details embedded within a subscription invoice.
 *
 * @see Invoice
 */
@Getter
public class InvoicePayment {

  /** Unique payment identifier. */
  private Long id;

  /** Payment status (e.g., approved, rejected, pending). */
  private String status;

  /** Additional detail about the payment status. */
  private String statusDetail;
}
