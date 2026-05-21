package com.mercadopago.resources.payment;

import com.mercadopago.resources.common.Address;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Resource that represents the shipping receiver's address for a MercadoPago payment.
 *
 * <p>Extends the base {@link Address} with additional fields for state, city, floor, and
 * apartment details, providing a complete delivery address.
 *
 * @see PaymentShipments#getReceiverAddress()
 */
@EqualsAndHashCode(callSuper = true)
@Getter
public class PaymentReceiverAddress extends Address {
  /** Name of the state or province. */
  private String stateName;

  /** Name of the city. */
  private String cityName;

  /** Floor number or identifier within the building. */
  private String floor;

  /** Apartment or unit number within the floor. */
  private String apartment;
}
