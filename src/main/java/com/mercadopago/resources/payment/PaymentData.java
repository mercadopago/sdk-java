package com.mercadopago.resources.payment;

import lombok.Getter;

/**
 * Resource that holds payment method data including rules, references, and external resource URLs.
 *
 * <p>Contains the configuration rules (discounts, fines, interest) applicable to the
 * payment method, along with internal and external reference identifiers.
 *
 * @see PaymentMethod#getData()
 */
@Getter
public class PaymentData {

  /** Rules defining discounts, fines, and interest for this payment method. */
  private PaymentRules rules;

  /** Internal reference identifier for the payment data. */
  private String referenceId;

  /** External reference identifier linking to the merchant's system. */
  private String externalReferenceId;

  /** URL of the external resource associated with the payment method (e.g. ticket page). */
  private String externalResourceUrl;
}
