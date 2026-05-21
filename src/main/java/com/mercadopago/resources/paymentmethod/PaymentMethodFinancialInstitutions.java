package com.mercadopago.resources.paymentmethod;

import lombok.Getter;

/**
 * Resource that represents a financial institution that supports a MercadoPago payment method.
 *
 * <p>Identifies a bank or financial entity that processes payments for a specific payment
 * method, such as ATM networks, banks for transfers, or card-issuing institutions.
 *
 * @see PaymentMethod#getFinancialInstitutions()
 */
@Getter
public class PaymentMethodFinancialInstitutions {
  /** Unique external identifier of the financial institution (e.g. bank code, ATM company ID). */
  private Long id;

  /** Human-readable name or description of the financial institution. */
  private String description;
}
