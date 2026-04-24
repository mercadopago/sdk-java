package com.mercadopago.resources.payment;

import java.time.OffsetDateTime;
import lombok.Getter;

/**
 * Resource that represents the card used to process a MercadoPago payment.
 *
 * <p>Contains masked card details (first six and last four digits), expiration information,
 * and the cardholder data. Full card numbers are never stored or returned for security
 * compliance.
 *
 * @see Payment
 * @see PaymentCardholder
 */
@Getter
public class PaymentCard {
  /** Unique identifier of the stored card in MercadoPago's vault. */
  private String id;

  /** Last four digits of the card number for display purposes. */
  private String lastFourDigits;

  /** First six digits (BIN) of the card number, used to identify the issuer and card type. */
  private String firstSixDigits;

  /** Year in which the card expires. */
  private int expirationYear;

  /** Month in which the card expires (1-12). */
  private int expirationMonth;

  /** Date and time when the card was first registered. */
  private OffsetDateTime dateCreated;

  /** Date and time of the most recent update to the card data. */
  private OffsetDateTime dateLastUpdated;

  /** Information about the cardholder (name and identification). */
  private PaymentCardholder cardholder;
}
