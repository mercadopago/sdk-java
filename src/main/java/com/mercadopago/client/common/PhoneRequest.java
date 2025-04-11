package com.mercadopago.client.common;

import com.mercadopago.client.payment.PaymentPayerPhoneRequest;
import lombok.Builder;
import lombok.Getter;

/** PhoneRequest class. */
@Getter
@Builder
public class PhoneRequest {
  /** Area code. */
  private final String areaCode;

  /** Phone number. */
  private final String number;
}
