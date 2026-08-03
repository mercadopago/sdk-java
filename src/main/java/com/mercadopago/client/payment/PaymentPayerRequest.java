package com.mercadopago.client.payment;

import com.mercadopago.client.common.IdentificationRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

/**
 * Request object representing the payer in a payment creation request.
 * Contains personal data such as name, email, identification document,
 * address, and phone needed to identify the person making the payment.
 */
@Getter
@Builder
public class PaymentPayerRequest {

  /** Authentication type used by the payer (e.g. "gmail", "facebook", "native"). */
  private final String authenticationType;

  /** Full name of the payer. */
  private final String name;

  /** Payer type identifier; mandatory when the payer is a registered Customer. */
  private final String type;

  /** Unique payer identifier in the MercadoPago platform. */
  private final String id;

  /** Email address of the payer. */
  private final String email;

  /** Personal identification document of the payer (e.g. CPF, DNI). */
  private final IdentificationRequest identification;

  /** First name of the payer. */
  private final String firstName;

  /** Last name of the payer. */
  private final String lastName;

  /** Entity type of the payer, applicable only for bank-transfer payments (e.g. "individual", "association"). */
  private final String entityType;

  /** Payer's address details. */
  private PaymentPayerAddressRequest address;

  /** Payer's phone details. */
  private PaymentPayerPhoneRequest phone;
}
