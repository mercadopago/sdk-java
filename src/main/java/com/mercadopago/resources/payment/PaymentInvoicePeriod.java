package com.mercadopago.resources.payment;

import lombok.Getter;

/**
 * Resource that represents the invoice period for a subscription-based MercadoPago payment.
 *
 * <p>Defines the billing cycle length and type for recurring or subscription payments,
 * indicating how frequently invoices are generated.
 *
 * @see PaymentTransactionData#getInvoicePeriod()
 */
@Getter
public class PaymentInvoicePeriod {

  /** Number representing the billing cycle length (e.g. 1, 3, 12). */
  private int period;

  /** Unit of the billing period (e.g. monthly, yearly, daily). */
  private String type;
}
