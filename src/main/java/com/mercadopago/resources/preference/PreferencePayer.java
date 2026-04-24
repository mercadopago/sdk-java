package com.mercadopago.resources.preference;

import com.mercadopago.resources.common.Address;
import com.mercadopago.resources.common.Identification;
import com.mercadopago.resources.common.Phone;

import java.time.OffsetDateTime;
import java.util.Date;

import lombok.Getter;

/**
 * Resource representing the buyer (payer) information in a checkout preference.
 *
 * <p>Contains the payer's personal details, contact information, address, and behavioral metadata
 * used by MercadoPago to enhance the checkout experience and fraud prevention analysis.
 *
 * @see Preference
 */
@Getter
public class PreferencePayer {
  /** First name of the payer. */
  private String name;

  /** Last name (surname) of the payer. */
  private String surname;

  /** Email address of the payer. */
  private String email;

  /** Phone contact information of the payer. */
  private Phone phone;

  /** Official identification document of the payer (type and number). */
  private Identification identification;

  /** Residential or billing address of the payer. */
  private Address address;

  /** Timestamp when the payer's user account was created. */
  private OffsetDateTime dateCreated;

  /** Timestamp of the payer's most recent purchase. */
  private OffsetDateTime lastPurchase;

  /** Authentication method used by the payer (e.g., gmail, facebook, native). */
  private String authenticationType;

  /** Whether the payer is a MercadoLibre Nivel 6 / Prime subscriber. */
  private Boolean isPrimeUser;

  /** Whether this is the payer's first online purchase. */
  private Boolean isFirstPurchaseOnline;

  /** Date when the payer registered on the platform. */
  private OffsetDateTime registrationDate;
}
