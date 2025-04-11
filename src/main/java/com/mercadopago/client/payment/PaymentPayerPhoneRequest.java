package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/** Payer's phone information. */
@Getter

public class PaymentPayerPhoneRequest {
  /** Area code. */
  private String areaCode;

  /** Phone number. */
  private String number;

}

