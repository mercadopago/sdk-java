package com.mercadopago.client.payment;

import com.mercadopago.client.common.IdentificationRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

/** PaymentPayerRequest class. */
@Getter
@Builder
public class PaymentPayerRequest {

  public enum AuthenticationType{
    Gmail, Facebook, WebNativa, Outros;
  }

  /** Payer's name. */
  private final  String name;

  /** Payer's surname (last name) */
  private final String surname;

  /** Payer's identification type (mandatory if the payer is a Customer). */
  private final String type;

  /** Payer's ID. */
  private final String id;

  /** Email of the payer. */
  private final String email;

  /** Payer's personal identification. */
  private final IdentificationRequest identification;

  /** Payer's first name. */
  private final String firstName;

  /** Payer's last name. */
  private final String lastName;

  /** Payer's entity type (only for bank transfers). */
  private final String entityType;

  /** Payer's address information. */
  private PaymentPayerAddressRequest address;

  /** Payer's phone information. */
  private PaymentPayerPhoneRequest phone;

  /** Payer's authentication type. */
  private final AuthenticationType authenticationType;

  /** Indicates if the payer is a prime user. */
 private final boolean isPrimeUser;

  /** Indicates if it's the payer's first purchase online. */
 private final boolean isFirstPurchaseOnline;

  /** Registration date of the payer. */
  private final Date registrationDate;

  /** Date of the last purchase made by the payer. */
  private final String lastPurchase;
}
