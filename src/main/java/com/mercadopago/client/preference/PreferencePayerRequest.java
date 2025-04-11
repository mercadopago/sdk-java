package com.mercadopago.client.preference;

import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import java.time.OffsetDateTime;
import java.util.Date;

import lombok.Builder;
import lombok.Getter;

/** Payer information. */
@Getter
@Builder
public class PreferencePayerRequest {
  /** Payer's name. */
  private final String name;

  /** Payer's surname. */
  private final String surname;

  /** Payer's email. */
  private final String email;

  /** Payer's phone. */
  private final PhoneRequest phone;

  /** Payer's identification. */
  private final IdentificationRequest identification;

  /** Payer's address. */
  private final AddressRequest address;

  /** Date of creation of the payer user. */
  private final OffsetDateTime dateCreated;

  /** Payer's authentication type. */
  private final String authenticationType;

  /** Indicates if the payer is a prime user. */
  private final boolean isPrimeUser;

  /** Indicates if it's the payer's first purchase online. */
  private final boolean isFirstPurchaseOnline;

  /** Registration date of the payer. */
  private final OffsetDateTime registrationDate;

  /** Date of the last purchase made by the payer. */
  private final OffsetDateTime lastPurchase;
}
