package com.mercadopago.resources.customer;

import lombok.Getter;

/**
 * Represents security code (CVV) metadata for a customer's saved card in the MercadoPago API.
 *
 * <p>Describes the expected length of the security code and its physical location on the card
 * (e.g., front or back), used for validation and UI rendering purposes.
 *
 * @see CustomerCard
 */
@Getter
public class CustomerCardSecurityCode {
  /** Expected length of the security code (e.g., 3 or 4 digits). */
  private Integer length;

  /** Physical location of the security code on the card (e.g., "back", "front"). */
  private String cardLocation;
}
