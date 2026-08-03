package com.mercadopago.resources.payment;

import com.mercadopago.resources.common.Identification;
import lombok.Getter;

/**
 * Resource that represents the holder of the card used in a MercadoPago payment.
 *
 * <p>Contains the name printed on the card and the cardholder's identification document,
 * which may be required for certain payment methods or regions.
 *
 * @see PaymentCard
 */
@Getter
public class PaymentCardholder {
  /** Full name of the cardholder as printed on the card. */
  private String name;

  /** Identification document of the cardholder (e.g. CPF, DNI). */
  private Identification identification;
}
