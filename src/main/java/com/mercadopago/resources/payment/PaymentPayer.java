package com.mercadopago.resources.payment;

import com.mercadopago.resources.common.Identification;
import lombok.Getter;

/** PaymentPayer class. */
@Getter
public class PaymentPayer {
  /** Payer's identification type (mandatory if the payer is a Customer). */
  private String type;

  /** Payer's ID. */
  private String id;

  /** Email of the payer. */
  private String email;

  /** Payer's personal identification. */
  private Identification identification;

  /** Payer's first name. */
  private String firstName;

  /** Payer's last name. */
  private String lastName;

  /** Payer's entity type (only for bank transfers). */
  private String entityType;
}
