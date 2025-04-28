package com.mercadopago.client.payment;

import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Getter;

/** PaymentAdditionalInfoPayerRequest class. */
@Getter
@Builder
public class PaymentAdditionalInfoPayerRequest{
  /** Payer first name. */
  private final String firstName;

  /** Payer last name. */
  private final String lastName;

  /** Payer phone. */
  private final PhoneRequest phone;

  /** Payer address. */
  private final AddressRequest address;

  /** Payer registration date. */
  private final OffsetDateTime registrationDate;

  /** Payer authentication type. */
  private final String authenticationType;

  /** If payer is prime user. */
  private final boolean isPrimeUser;

  /** If is first online purchase. */
  private final boolean isFirstPurchaseOnline;

  /** Date of last purchase. */
  private final OffsetDateTime lastPurchase;

  /** Payer's personal identification. */
  private final IdentificationRequest identification;
}
