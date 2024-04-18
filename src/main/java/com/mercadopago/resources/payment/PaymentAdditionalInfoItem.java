package com.mercadopago.resources.payment;

import com.mercadopago.resources.common.Address;
import java.time.OffsetDateTime;
import lombok.Getter;

/** PaymentAdditionalInfoPayer class. */
@Getter
public class PaymentAdditionalInfoPayer {
  /** Payer's name. */
  private String firstName;

  /** Payer's last name. */
  private String lastName;

  /** Payer's phone. */
  private PaymentPhone phone;

  /** Payer's address. */
  private Address address;

  /** Date of registration of the payer on your site. */
  private OffsetDateTime registrationDate;
}
