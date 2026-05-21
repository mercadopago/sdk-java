package com.mercadopago.client.payment;

import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Getter;

/**
 * Extended payer information included in the additional-info section of a payment request.
 * Provides enriched buyer data such as registration history and purchase behavior, which
 * is used by MercadoPago's fraud-prevention engine to improve approval rates.
 */
@Getter
@Builder
public class PaymentAdditionalInfoPayerRequest {
  /** First name of the payer. */
  private final String firstName;

  /** Last name of the payer. */
  private final String lastName;

  /** Payer's phone information. */
  private final PhoneRequest phone;

  /** Payer's address information. */
  private final AddressRequest address;

  /** Date when the payer registered on the merchant's platform. */
  private final OffsetDateTime registrationDate;

  /** Authentication method used by the payer (e.g. "gmail", "facebook", "native"). */
  private final String authenticationType;

  /** Whether the payer is a prime/premium subscriber on the merchant's platform. */
  private final boolean isPrimeUser;

  /** Whether this is the payer's first online purchase on the merchant's platform. */
  private final boolean isFirstPurchaseOnline;

  /** Date and time of the payer's last purchase on the merchant's platform. */
  private final OffsetDateTime lastPurchase;

  /** Personal identification document of the payer (e.g. CPF, DNI). */
  private final IdentificationRequest identification;
}
