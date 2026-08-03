package com.mercadopago.resources.payment;

import com.mercadopago.resources.common.Address;
import java.time.OffsetDateTime;
import lombok.Getter;

/**
 * Resource that holds extended payer information within the additional info of a MercadoPago payment.
 *
 * <p>Provides the payer's name, phone, address, and registration date on the merchant's platform.
 * This data supplements the primary {@link PaymentPayer} and helps improve fraud detection.
 *
 * @see PaymentAdditionalInfo#getPayer()
 */
@Getter
public class PaymentAdditionalInfoPayer {
  /** First name of the payer as registered on the merchant's platform. */
  private String firstName;

  /** Last name of the payer as registered on the merchant's platform. */
  private String lastName;

  /** Phone contact information of the payer. */
  private PaymentPhone phone;

  /** Residential or billing address of the payer. */
  private Address address;

  /** Date and time when the payer registered on the merchant's site. */
  private OffsetDateTime registrationDate;
}
