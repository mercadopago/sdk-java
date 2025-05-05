package com.mercadopago.resources.payment;

import com.mercadopago.resources.common.Identification;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.Date;

/** PaymentPayer class. */
@Getter
public class PaymentPayer {
  /** Payer's identification type (mandatory if the payer is a Customer). */
  private String type;

  /** Payer's ID. */
  private String id;

  /** Email of the payer. */
  private String email;

  /** Payer's personal identification. */
  private Identification identification;

  /** Payer's first name. */
  private String firstName;

  /** Payer's last name. */
  private String lastName;

  /** Payer's entity type (only for bank transfers). */
  private String entityType;

  /** Payer's authentication type. */
  private String authenticationType;

  /** Indicates if the payer is a prime user. */
  private boolean isPrimeUser;

  /** Indicates if this is the first purchase online for the payer. */
  private boolean isFirstPurchaseOnline;

  /** Registration date of the payer. */
  private OffsetDateTime registrationDate;
}
