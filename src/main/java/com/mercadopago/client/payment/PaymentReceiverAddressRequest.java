package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/**
 * Request object representing the receiver's address for a shipment associated with a payment.
 * Contains the full postal address where purchased goods will be delivered.
 */
@Getter
@Builder
public class PaymentReceiverAddressRequest {
  /** State or province name. */
  private final String stateName;

  /** City name. */
  private final String cityName;

  /** Floor number or identifier within a building. */
  private final String floor;

  /** Apartment or unit number within a building. */
  private final String apartment;

  /** Postal or ZIP code. */
  private final String zipCode;

  /** Street name of the delivery address. */
  private final String streetName;

  /** Street number of the delivery address. */
  private final String streetNumber;
}
