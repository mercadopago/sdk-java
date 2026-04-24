package com.mercadopago.client.preference;

import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import java.time.OffsetDateTime;
import java.util.Date;

import lombok.Builder;
import lombok.Getter;

/**
 * Payer (buyer) information for a checkout preference, including personal details, contact
 * information, and purchase history indicators used for fraud prevention.
 *
 * @see com.mercadopago.client.preference.PreferenceRequest
 */
@Getter
@Builder
public class PreferencePayerRequest {
  /** Payer's first name. */
  private final String name;

  /** Payer's last name. */
  private final String surname;

  /** Payer's email address. */
  private final String email;

  /** Payer's phone contact information. */
  private final PhoneRequest phone;

  /** Payer's identification document details. */
  private final IdentificationRequest identification;

  /** Payer's address information. */
  private final AddressRequest address;

  /** Date when the payer's account was created. */
  private final OffsetDateTime dateCreated;

  /** Authentication type used by the payer (e.g., Gmail, Facebook, native). */
  private final String authenticationType;

  /** Whether the payer has a prime/loyalty membership. */
  private final boolean isPrimeUser;

  /** Whether this is the payer's first online purchase. */
  private final boolean isFirstPurchaseOnline;

  /** Date when the payer registered on the platform. */
  private final OffsetDateTime registrationDate;

  /** Date of the payer's most recent purchase. */
  private final OffsetDateTime lastPurchase;
}
