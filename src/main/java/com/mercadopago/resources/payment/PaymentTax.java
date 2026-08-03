package com.mercadopago.resources.payment;

import java.math.BigDecimal;
import lombok.Getter;

/**
 * Resource that represents a tax applied to a MercadoPago payment.
 *
 * <p>Defines the type and value of a tax charge included in the payment, such as
 * IVA, ICMS, or other regional tax obligations.
 *
 * @see Payment#getTaxes()
 */
@Getter
public class PaymentTax {
  /** Type of tax applied (e.g. IVA, ICMS, ISS). */
  private String type;

  /** Monetary value of the tax. */
  private BigDecimal value;
}
