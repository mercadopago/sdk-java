package com.mercadopago.client.payment;

import com.mercadopago.client.common.IdentificationRequest;
import lombok.Builder;
import lombok.Getter;

/**
 * Request object representing a passenger associated with a travel-related item.
 * Used within the category descriptor to provide passenger identity details
 * for airline tickets and similar travel purchases.
 */
@Getter
@Builder
public class PaymentPassengerRequest {
  /** First name of the passenger. */
  private final String firstName;

  /** Last name of the passenger. */
  private final String lastName;

  /** Personal identification document of the passenger (e.g. passport, national ID). */
  private final IdentificationRequest identification;
}
