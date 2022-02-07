package com.mercadopago.resources.payment;

import java.time.OffsetDateTime;
import lombok.Getter;

/** PaymentCard class. */
@Getter
public class PaymentCard {
  /** Id of the card. */
  private String id;

  /** Last four digits of card number. */
  private String lastFourDigits;

  /** First six digit of card number. */
  private String firstSixDigits;

  /** Card expiration year. */
  private int expirationYear;

  /** Card expiration month. */
  private int expirationMonth;

  /** Creation date of card. */
  private OffsetDateTime dateCreated;

  /** Last update of data from the card. */
  private OffsetDateTime dateLastUpdated;

  /** Card's owner data. */
  private PaymentCardholder cardholder;
}
