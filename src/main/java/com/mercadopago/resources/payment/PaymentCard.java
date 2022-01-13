package com.mercadopago.resources.payment;

import java.util.Date;
import lombok.Data;

/** PaymentCard class. */
@Data
public class PaymentCard {
  public String id;

  public String lastFourDigits;

  public String firstSixDigits;

  public int expirationYear;

  public int expirationMonth;

  public Date dateCreated;

  public Date dateLastUpdated;

  public PaymentCardholder cardholder;
}
