package com.mercadopago.client.common;

import lombok.Builder;
import lombok.Data;

/** IdentificationRequest class. */
@Data
@Builder
public class IdentificationRequest {
  private String type;

  private String number;

  /**
   * IdentificationRequest class.
   *
   * @param type type
   * @param number number
   */
  public IdentificationRequest(String type, String number) {
    this.type = type;
    this.number = number;
  }
}
