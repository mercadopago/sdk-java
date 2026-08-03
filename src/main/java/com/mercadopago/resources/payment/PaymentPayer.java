package com.mercadopago.resources.payment;

import com.mercadopago.resources.common.Identification;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.Date;

/**
 * Resource that represents the payer associated with a MercadoPago payment.
 *
 * <p>Contains personal information about the buyer including identification, contact details,
 * authentication type, and registration data. This information is used for fraud prevention
 * and to process the payment correctly.
 *
 * @see Payment
 */
@Getter
public class PaymentPayer {
  /** Payer type, mandatory when the payer is a registered Customer (e.g. customer, guest). */
  private String type;

  /** Unique identifier of the payer in the MercadoPago platform. */
  private String id;

  /** Email address of the payer. */
  private String email;

  /** Personal identification document of the payer (e.g. CPF, DNI, CURP). */
  private Identification identification;

  /** First name of the payer. */
  private String firstName;

  /** Last name of the payer. */
  private String lastName;

  /** Entity type of the payer, applicable only for bank transfer payments (e.g. individual, association). */
  private String entityType;

  /** Authentication type used to verify the payer's identity. */
  private String authenticationType;

  /** Whether the payer is a MercadoLibre Loyalty (prime/level 6) subscriber. */
  private boolean isPrimeUser;

  /** Whether this is the payer's first online purchase. */
  private boolean isFirstPurchaseOnline;

  /** Date and time when the payer registered on the merchant's platform. */
  private OffsetDateTime registrationDate;
}
