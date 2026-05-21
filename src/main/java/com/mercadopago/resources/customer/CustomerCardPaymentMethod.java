package com.mercadopago.resources.customer;

import lombok.Getter;

/**
 * Represents the payment method associated with a customer's saved card in the MercadoPago API.
 *
 * <p>Contains details about the payment method type, name, and thumbnail images that correspond
 * to the card brand (e.g., Visa, Mastercard).
 *
 * @see CustomerCard
 */
@Getter
public class CustomerCardPaymentMethod {
  /** Unique identifier of the payment method. */
  private String id;

  /** Display name of the payment method (e.g., Visa, Mastercard). */
  private String name;

  /** Type identifier of the payment method (e.g., credit_card, debit_card). */
  private String paymentTypeId;

  /** URL of the payment method thumbnail image. */
  private String thumbnail;

  /** URL of the payment method thumbnail image served over HTTPS. */
  private String secureThumbnail;
}
