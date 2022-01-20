package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/** PaymentReceiverAddressRequest class. */
@Getter
@Builder
public class PaymentReceiverAddressRequest {
  /** State. */
  private final String stateName;

  /** City. */
  private final String cityName;

  /** Floor. */
  private final String floor;

  /** Apartment. */
  private final String apartment;

  /** Zip code. */
  private final String zipCode;

  /** Street name. */
  private final String streetName;

  /** Street number. */
  private final String streetNumber;
}
